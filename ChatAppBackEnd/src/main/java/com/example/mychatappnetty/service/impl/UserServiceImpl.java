package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.dao.FriendDao;
import com.example.mychatappnetty.dao.FriendRequestDao;
import com.example.mychatappnetty.dao.UserDao;
import com.example.mychatappnetty.entity.FriendsRequest;
import com.example.mychatappnetty.entity.MyFriends;
import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.dto.vo.MyFriendsVO;
import com.example.mychatappnetty.enums.MsgActionEnum;
import com.example.mychatappnetty.enums.MsgSignFlagEnum;
import com.example.mychatappnetty.enums.SearchFriendsStatusEnum;
import com.example.mychatappnetty.enums.StateEnum;
import com.example.mychatappnetty.netty.ChatHandler;
import com.example.mychatappnetty.netty.entity.DataContent;
import com.example.mychatappnetty.service.UserService;
import com.example.mychatappnetty.util.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String KEY = "com.example.chatapp.key";

    @Autowired
    private UserDao userDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private FriendRequestDao friendRequestDao;


    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private QRCodeUtil qrCodeUtil;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * Register User
     * 1. Nick name is the same as username by default.
     * 2. setStatus : INACTIVE by default.
     * @param user
     * TODO: setFaceImage ,setClientId, setQrcode
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users register(Users user) {

        user.setId(UUIDUtil.generateUUID());
        user.setFaceImage("");
        user.setFaceImageBig("");
        user.setMomentBackgroundImg("");

        // QRCode CONTENT : my_chat_app:<username>
        // TODO: encrypt by DES algorithm
        String qrCodeContent = "my_chat_app:" + DESUtil.encrypt(user.getUsername(),KEY);

        System.out.println(DESUtil.encrypt(user.getUsername(),KEY));

        String multipartFileName = user.getUsername() +"userqrcode.png";

        // Create MultipartFile object for uploading.
        MultipartFile qrCodeFile = qrCodeUtil.createQRCode(qrCodeContent,multipartFileName);

        String qrcodeURL = null;

        try {
            qrcodeURL = fastDFSClient.uploadFile(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(qrcodeURL != null) user.setQrcode(qrcodeURL);

        user.setStatus(StateEnum.ACTIVE.getStateCode());
        user.setNickname(user.getUsername());

        try {
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        userDao.insertUser(user);

        return user;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users updateUserStatus(Users userCondition) {
        userDao.updateUserStatus(userCondition);
        return userDao.queryUser(userCondition);
    }

    @Override
    public Users loginUser(Users user) {
        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users searchUserByCondition(Users userCondition) {
        if(userCondition.getUsername() != null){
            return userDao.queryUser(userCondition);
        }
        if(userCondition.getUserEmail()!= null){
            return userDao.queryUser(userCondition);
        }

        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer preConditionSearchFriend(String fromUserId, String toUserName) {

        Users userCondition = new Users();
        userCondition.setUsername(toUserName);
        Users user =  this.searchUserByCondition(userCondition);

        if(user == null){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.getStatus();
        }

        if(user.getId().equals(fromUserId)){
            return SearchFriendsStatusEnum.NOT_YOURSELF.getStatus();
        }

        MyFriends myFriendsCondition  = new MyFriends();
        myFriendsCondition.setMyUserId(fromUserId);
        myFriendsCondition.setMyFriendUserId(user.getId());

        List<MyFriends> myFriendsRes  = friendDao.searchFriendShipByCondition(myFriendsCondition);

        if(myFriendsRes != null && myFriendsRes.size() !=0){
            return  SearchFriendsStatusEnum.ALREADY_FRIENDS.getStatus();
        }

        return SearchFriendsStatusEnum.SUCCESS.getStatus();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer saveFriends(String myUserId, String myFriendUserId) {
        return friendDao.insertFriendShip(MyFriends.builder()
                                        .id(UUIDUtil.generateUUID())
                                        .myUserId(myUserId)
                                        .myFriendUserId(myFriendUserId)
                                        .build());

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void acceptUserRequest(String senderUserId, String accepterUserId) {
        this.saveFriends(accepterUserId,senderUserId);
        this.saveFriends(senderUserId,accepterUserId);
        friendRequestDao.deleteFriendsRequestByCondition(FriendsRequest.builder()
                .sendUserId(senderUserId)
                .acceptUserId(accepterUserId)
                .build());

        // TODO: Utilize WebSocket to send a notification to the sender
        String receiverChannelLongId = (String) redisTemplate.opsForValue().get(senderUserId);

        if (StringUtils.isNotBlank(receiverChannelLongId)){
            // find channel by its long id
            Channel findChannel = null;

            for(Channel channel: ChatHandler.getUsers()){
                if (channel.id().asLongText().equals(receiverChannelLongId)){
                    findChannel = channel;
                }
            }

            if (findChannel != null) {
                DataContent dataContent = new DataContent();
                dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
                findChannel.writeAndFlush(
                    new TextWebSocketFrame(
                        JsonUtils.objectToJson(dataContent)));
            }
        }

    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyFriendsVO> getFriendsByUserId(String userId) {
        return friendDao.queryFriendsByUserId(userId);
    }


}
