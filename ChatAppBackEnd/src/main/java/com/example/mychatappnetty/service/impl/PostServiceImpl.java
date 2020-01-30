package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.dao.*;
import com.example.mychatappnetty.entity.*;
import com.example.mychatappnetty.entity.dto.vo.MyFriendsVO;
import com.example.mychatappnetty.entity.dto.vo.PostVO;
import com.example.mychatappnetty.service.PostService;
import com.example.mychatappnetty.util.PageCalculator;
import com.example.mychatappnetty.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsDao postsDao;

    @Autowired
    private PostImgDao postImgDao;

    @Autowired
    private NewsFeedDao newsFeedDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertPost(Posts posts) {
        return postsDao.insertPost(posts);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int batchInsertPostImg(List<PostImg> postImgList) {
        return postImgDao.batchInsertPostImg(postImgList);
    }

    /**
     * @description
     * @author admin
     * @updateTime 2019/11/27 11:45
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Async
    public void fanout(Posts posts) {
        String ownerId = posts.getUserId();
        List<MyFriends> myFriendsList =
            friendDao.searchFriendShipByCondition(MyFriends.builder().myUserId(ownerId).build());

        List<NewsFeed> newsFeedList = new ArrayList<>();

        myFriendsList.forEach(
            myFriends -> {
                NewsFeed newsFeed =
                    NewsFeed.builder()
                        .id(UUIDUtil.generateUUID())
                        .postId(posts.getId())
                        .userId(myFriends.getMyFriendUserId())
                        .createTime(posts.getPostCreateTime())
                        .build();
                newsFeedList.add(newsFeed);
            });

        // also, it is necessary to include yourself.
        NewsFeed newsFeed =
            NewsFeed.builder()
                .id(UUIDUtil.generateUUID())
                .postId(posts.getId())
                .userId(ownerId)
                .createTime(posts.getPostCreateTime())
                .build();
        newsFeedList.add(newsFeed);

        newsFeedDao.batchInsertNewsFeed(newsFeedList);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PostVO queryPostsByCondition(String userId, Integer pageNumber, Integer pageSize) {

        int rowIndex = PageCalculator.calculateRowIndex(pageNumber, pageSize);

        List<NewsFeed> newsFeedList = newsFeedDao.queryNewsFeedByUserId(userId, rowIndex, pageSize);

        List<String> postIdLists = new ArrayList<>();

        newsFeedList.forEach(newsFeed -> postIdLists.add(newsFeed.getPostId()));

        List<Posts> posts = postsDao.batchQueryPostsByPostId(postIdLists);

        List<MyFriendsVO> owners = new ArrayList<>();

        for (Posts post : posts) {
            List<PostImg> postImgs = postImgDao.queryPostImgListByPostId(post.getId());
            post.setPostImgList(postImgs);
            Users users = userDao.queryUser(Users.builder().id(post.getUserId()).build());
            MyFriendsVO myFriendsVO = new MyFriendsVO();
            myFriendsVO.setFriendNickname(users.getNickname());
            myFriendsVO.setFriendImageBig(users.getFaceImageBig());
            owners.add(myFriendsVO);
        }

        return PostVO.builder().count(posts.size()).posts(posts).owners(owners).build();
    }
}
