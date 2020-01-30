package com.example.mychatappnetty.service;

import com.example.mychatappnetty.entity.ChatMsg;
import java.util.List;

/**
 * @author tan3
 * @ClassName ChatService.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 16 : 21
 */
public interface ChatService {

    /**
     * Save Message to the database and
     * @param chatMsg
     * @return
     */
    String saveMsg(ChatMsg chatMsg);


    /**
     * batch update message to convert states to signed
     * @param msgIdList
     */
    void batchUpdateMsgSigned(List<String> msgIdList);


    List<ChatMsg> queryUnreadChatMsg(String acceptUserId);


}
