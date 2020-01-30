package com.example.mychatappnetty.dao;


import com.example.mychatappnetty.entity.ChatMsg;
import java.util.List;

/**
 * @author tan3
 * @ClassName ChatDao.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 16 : 40
 */
public interface ChatDao {

    int insertMsg(ChatMsg chatMsg);

    int batchUpdateMsgSigned(List<String> msgIdList);

    List<ChatMsg> queryUnreadMsg(String acceptUserId);
}
