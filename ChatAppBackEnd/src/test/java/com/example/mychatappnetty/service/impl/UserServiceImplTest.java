package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.example.mychatappnetty.dao.FriendDao;
import com.example.mychatappnetty.entity.MyFriends;
import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.dto.vo.UsersVO;
import com.example.mychatappnetty.service.UserService;
import com.example.mychatappnetty.util.MD5Util;
import com.example.mychatappnetty.util.UUIDUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceImplTest  extends MyChatappNettyApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendDao friendDao;

    @Test
    @Ignore
    public void register() throws Exception {
        Users user = new Users();
        user.setId(UUIDUtil.generateUUID());
        user.setUsername("test2");
        user.setPassword(MD5Util.getMD5Str("test2"));
        user.setUserEmail("xxxxx@gmail.com");
        userService.register(user);
    }

    @Test
    @Ignore
    public void updateUserStatus(){
        Users userCondition = new Users();
        userCondition.setId("a51675a9840c400cb02f6846b8558c31");
        userCondition.setFaceImage("testFaceImageURL");
        userCondition.setFaceImageBig("testFaceImageBigURL");
        Users updatedUser = userService.updateUserStatus(userCondition);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(updatedUser,usersVO);

        assertEquals(usersVO.getId(), "a51675a9840c400cb02f6846b8558c31");
        assertEquals(usersVO.getFaceImage(),"testFaceImageURL");
        assertEquals(usersVO.getFaceImageBig(),"testFaceImageBigURL");
    }

    @Test
    @Ignore
    public void preConditionSearchFriend(){
        String fromUserId = "e89e1a24ec2646568ed84363b5419c2b";
        String toUserName0 = "ovo";
        String toUserName1 = "UYTGJK";
        String toUserName2 = "test2";


        // NOT_YOURSELF(2, "Cannot add yourself..."),
        assertEquals(java.util.Optional.ofNullable(userService.preConditionSearchFriend(fromUserId, toUserName0)),
                java.util.Optional.of(2));


        //  USER_NOT_EXIST(1, "User Not exist..."),
        assertEquals(java.util.Optional.ofNullable(userService.preConditionSearchFriend(fromUserId, toUserName1)),
                java.util.Optional.of(1));

        // SUCCESS(0, "OK"),
        assertEquals(java.util.Optional.ofNullable(userService.preConditionSearchFriend(fromUserId, toUserName2)),
                java.util.Optional.of(0));


        // add relationship between two person
        MyFriends relationship = new MyFriends();
        relationship.setId(UUIDUtil.generateUUID());
        relationship.setMyFriendUserId("ec93a349323547c08f4b734d0c698bda");// test2 user id
        relationship.setMyUserId("e89e1a24ec2646568ed84363b5419c2b");

        friendDao.insertFriendShip(relationship);

        //ALREADY_FRIENDS(3, "He/She has already been your friend...");
        assertEquals(java.util.Optional.ofNullable(userService.preConditionSearchFriend(fromUserId, toUserName2)),
                java.util.Optional.of(3));

    }

}
