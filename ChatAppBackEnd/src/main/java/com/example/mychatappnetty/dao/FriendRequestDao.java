package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.entity.FriendsRequest;
import com.example.mychatappnetty.entity.dto.vo.FriendsRequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FriendRequestDao {


    List<FriendsRequest> queryFriendsRequestByCondition(FriendsRequest friendsRequest);


    int insertFriendsRequest(FriendsRequest friendsRequest);


    int deleteFriendsRequestByCondition(FriendsRequest friendsRequest);


    List<FriendsRequestVO> queryFriendsRequestList(String acceptUserId);




}
