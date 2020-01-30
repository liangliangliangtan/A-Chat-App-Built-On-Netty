package com.example.mychatappnetty.dao;


import com.example.mychatappnetty.entity.MyFriends;
import com.example.mychatappnetty.entity.dto.vo.MyFriendsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendDao {

    int insertFriendShip(MyFriends myFriends);

    /**
     * Search User by condition can return list of relationships.
     *
     * @param myFriendsCondition
     * @return
     */
    List<MyFriends> searchFriendShipByCondition(MyFriends myFriendsCondition);


    List<MyFriendsVO> queryFriendsByUserId(String userId);

}
