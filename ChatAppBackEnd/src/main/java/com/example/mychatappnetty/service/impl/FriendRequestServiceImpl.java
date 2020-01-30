package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.dao.FriendRequestDao;
import com.example.mychatappnetty.entity.FriendsRequest;
import com.example.mychatappnetty.entity.dto.vo.FriendsRequestVO;
import com.example.mychatappnetty.service.FriendRequestService;
import com.example.mychatappnetty.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

  @Autowired private FriendRequestDao friendRequestDao;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)

  /**
   * @Description: please enter description for method here
   *
   * @param sendUserId
   * @param acceptUserId
   * @return: java.lang.Integer @Creator: tan3 @Date: 2019/11/27 13:03
   */
  public Integer sentFriendShipRequest(String sendUserId, String acceptUserId) {

    List<FriendsRequest> friendsRequestList =
        friendRequestDao.queryFriendsRequestByCondition(
            FriendsRequest.builder().sendUserId(sendUserId).acceptUserId(acceptUserId).build());

    if (friendsRequestList == null || friendsRequestList.size() == 0) {
      Integer affectRow =
          friendRequestDao.insertFriendsRequest(
              FriendsRequest.builder()
                  .id(UUIDUtil.generateUUID())
                  .sendUserId(sendUserId)
                  .acceptUserId(acceptUserId)
                  .requestDateTime(new Date())
                  .build());
      return affectRow;
    }

    return null;
  }

  @Override
  public List<FriendsRequestVO> getFriendsRequestList(String acceptUserId) {
    return friendRequestDao.queryFriendsRequestList(acceptUserId);
  }

  /**
   * Delete friendship request by sender UserId and accepter User Id
   *
   * @param sendUserId
   * @param acceptUserId
   * @return
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Integer deleteFriendShipRequest(String sendUserId, String acceptUserId) {
    return friendRequestDao.deleteFriendsRequestByCondition(
        FriendsRequest.builder().sendUserId(sendUserId).acceptUserId(acceptUserId).build());
  }
}
