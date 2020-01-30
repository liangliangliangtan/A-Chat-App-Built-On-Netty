package com.example.mychatappnetty.enums;

/**
 * @author tan3
 * @ClassName MsgActionEnum.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 10 : 27
 */
public enum MsgActionEnum {

    CONNECT(1, "init Connection (or reconnection)"),
    CHAT(2, "Send message  from client to netty server"),
    SIGNED(3, " mark unread message as read"),
    KEEPALIVE(4, "HeartBeat Check"),
    PULL_FRIEND(5, "PULL FRIEND");


    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

}
