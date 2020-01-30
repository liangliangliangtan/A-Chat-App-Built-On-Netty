package com.example.mychatappnetty.service;

import com.example.mychatappnetty.entity.dto.vo.FriendsRequestVO;

import java.util.List;

public interface FriendRequestService {


    /**
     * 1. Search if the request is already exists in the database.
     * If the request already been sent from sendUserId, to acceptUserId ,return null
     *
     * 2. create relationship and insert into the database
     *
     * @param sendUserId
     * @param acceptUserId
     * @return
     */
    Integer sentFriendShipRequest(String sendUserId, String acceptUserId);



    List<FriendsRequestVO> getFriendsRequestList(String acceptUserId);


    /**
     * Delete friendship relationships based on sender id and accepter id.
     * @param sendUserId
     * @param acceptUserId
     * @return
     */
    Integer deleteFriendShipRequest(String sendUserId, String acceptUserId);

}
