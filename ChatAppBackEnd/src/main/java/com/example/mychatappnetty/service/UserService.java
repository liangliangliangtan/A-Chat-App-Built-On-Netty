package com.example.mychatappnetty.service;

import com.example.mychatappnetty.entity.Users;
import com.example.mychatappnetty.entity.dto.vo.MyFriendsVO;

import java.util.List;

public interface UserService {

    /**
     * register User,
     * @param user
     */
    Users register(Users user);


    /**
     * Update User by Condition.
     * @param user
     * @return
     */
    Users updateUserStatus(Users user);


    /**
     * Login
     * @param user
     * @return
     */
    Users loginUser(Users user);


    /**
     * Search User by username or email
     * @param userCondition
     * @return
     */
    Users searchUserByCondition(Users userCondition);

    /**
     * Validate if it is possible for User[fromUserId] to add Friend with username[toUserName]
     * Not able to add scenario
     * 1. If the user add itself
     * 2. The friendship between two user already existed
     * 3. The toUserName Does not exist
     * @param fromUserId
     * @param toUserName
     * @return The state code defined in the SearchFriendsStatusEnum
     */
    Integer preConditionSearchFriend(String fromUserId, String toUserName);



    Integer saveFriends(String myUserId, String myFriendUserId);


    /**
     * Accept friend request from the sender.
     *
     * 1. Insert friends relationships (dual)
     *
     * 2. Delete the friend request.
     *
     *
     * @param senderUserId
     * @param accepterUserId
     */
    void acceptUserRequest(String senderUserId, String accepterUserId);


    List<MyFriendsVO> getFriendsByUserId(String userId);



}
