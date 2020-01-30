package com.example.mychatappnetty.enums;


/**
 *
 * @Description: the states of Ignoring or accepting friends requests.
 */
public enum OperatorFriendRequestTypeEnum {

    IGNORE(0, "ignore"),
    ACCEPT(1, "accept");

    public final Integer type;
    public final String msg;

    OperatorFriendRequestTypeEnum(Integer type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {

        return msg;
    }

    public static String getMsgByType(Integer type) {
        for (OperatorFriendRequestTypeEnum operType : OperatorFriendRequestTypeEnum.values()) {
            if (operType.getType() == type) {
                return operType.msg;
            }
        }
        return null;
    }

}
