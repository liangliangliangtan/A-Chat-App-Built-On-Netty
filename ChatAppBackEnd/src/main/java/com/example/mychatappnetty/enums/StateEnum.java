package com.example.mychatappnetty.enums;


public enum StateEnum {


    ACTIVE(1), INACTIVE(0);

    private Integer stateCode;

    StateEnum(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getStateCode() {
        return stateCode;
    }
}
