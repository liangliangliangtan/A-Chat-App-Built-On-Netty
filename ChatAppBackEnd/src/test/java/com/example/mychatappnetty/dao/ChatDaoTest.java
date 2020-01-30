package com.example.mychatappnetty.dao;

import static org.junit.Assert.*;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.example.mychatappnetty.entity.ChatMsg;
import com.example.mychatappnetty.enums.MsgSignFlagEnum;
import com.example.mychatappnetty.util.UUIDUtil;
import java.util.Date;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tan3
 * @ClassName ChatDaoTest.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 24 - 10 : 22
 */
public class ChatDaoTest extends MyChatappNettyApplicationTests {


    @Autowired
    private ChatDao chatDao;

    @Test
    @Ignore
    public void insertMsg() {
        ChatMsg chatMsg = new ChatMsg();

        chatMsg.setSendUserId("testsenderId");
        chatMsg.setAcceptUserId("exexex");
        chatMsg.setChatMsgId(UUIDUtil.generateUUID());
        chatMsg.setSignFlag(MsgSignFlagEnum.unsigned.getType());
        chatMsg.setCreateTime(new Date());

        chatMsg.setChatMsg("testmesssage");
        chatDao.insertMsg(chatMsg);

    }

    @Test
    public void queryUnreadMsg() {
        String acceptUserId = "e2a0da33c3874ece845ed82be2730138";
        List<ChatMsg> resultList = chatDao.queryUnreadMsg(acceptUserId);
        assertEquals(resultList.size(), 7);
    }
}
