package com.example.mychatappnetty.netty.entity;

import java.io.Serializable;

/**
 * @author tan3
 * @ClassName ChatMsg.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 10 : 19
 */
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = -2581957243558259507L;

    private String senderId;
    private String receiverId;
    private String msg;
    private String msgId;


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
