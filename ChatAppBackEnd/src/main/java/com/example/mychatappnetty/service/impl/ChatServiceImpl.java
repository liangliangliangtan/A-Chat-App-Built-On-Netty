package com.example.mychatappnetty.service.impl;

import com.example.mychatappnetty.dao.ChatDao;
import com.example.mychatappnetty.entity.ChatMsg;
import com.example.mychatappnetty.enums.MsgSignFlagEnum;
import com.example.mychatappnetty.service.ChatService;
import com.example.mychatappnetty.util.DESUtil;
import com.example.mychatappnetty.util.UUIDUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tan3
 * @ClassName ChatServiceImpl.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 16 : 22
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(ChatMsg chatMsg) {

        String msgId = UUIDUtil.generateUUID();

        // generate message id and set its date and state.
        chatMsg.setChatMsgId(msgId);
        chatMsg.setCreateTime(new Date());
        chatMsg.setSignFlag(MsgSignFlagEnum.unsigned.getType());

        // encrypt message  with DES algorithm.
        String key =  chatMsg.getSendUserId() + "#" + chatMsg.getAcceptUserId();
        String encryptMsg  = DESUtil.encrypt(chatMsg.getChatMsg(), key);
        chatMsg.setChatMsg(encryptMsg);

        chatDao.insertMsg(chatMsg);
        return msgId;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void batchUpdateMsgSigned(List<String> msgIdList) {
        if (msgIdList == null || msgIdList.size() == 0){
            return ;
        }
        chatDao.batchUpdateMsgSigned(msgIdList);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ChatMsg> queryUnreadChatMsg(String acceptUserId) {
        return chatDao.queryUnreadMsg(acceptUserId);
    }
}
