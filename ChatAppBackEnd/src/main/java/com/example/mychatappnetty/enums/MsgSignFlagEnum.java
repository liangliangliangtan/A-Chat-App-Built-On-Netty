package com.example.mychatappnetty.enums;

/**
 * @author tan3
 * @ClassName MsgSignFlagEnum.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 24 - 10 : 16
 */
public enum MsgSignFlagEnum {

    unsigned(0, "unread message"),
    signed(1, " read message");

    private final Integer type;

    private final String content;


    MsgSignFlagEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
