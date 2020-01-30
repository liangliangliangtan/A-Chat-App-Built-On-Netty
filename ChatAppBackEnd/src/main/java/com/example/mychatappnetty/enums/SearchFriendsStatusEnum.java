package com.example.mychatappnetty.enums;

public enum SearchFriendsStatusEnum {

    SUCCESS(0, "OK"),
    USER_NOT_EXIST(1, "User Not exist..."),
    NOT_YOURSELF(2, "Cannot add yourself..."),
    ALREADY_FRIENDS(3, "He/She has already been your friend...");

    private Integer status;
    private String msg;

    SearchFriendsStatusEnum(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * Given a status code, return its corresponding msg.
     * if the state code does not exist, return null/
     * @param status
     * @return
     */
    public static String getMsgByState(Integer status) {
        for (SearchFriendsStatusEnum type : SearchFriendsStatusEnum.values()) {
            if (type.getStatus() == status) {
                return type.msg;
            }
        }
        return null;
    }
}
