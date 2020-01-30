package com.example.mychatappnetty.netty.entity;

import java.io.Serializable;

/**
 * @author tan3
 * @ClassName DataContent.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 10 : 14
 */
public class DataContent implements Serializable {


    private static final long serialVersionUID = 4716384416217827249L;

    private Integer action; // Message State Code

    private ChatMsg chatMsg; // wrapper class for received message.

    private String extend; // extended arguments


    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
