package com.example.mychatappnetty.enums;

/**
 * Time Constant in ms
 */
public enum TimeEnum {

    ONE_MIN(60000), ONE_HOUR(3600000),
    ONE_DAY(86400000),TEN_MIN(600000);

    private Integer ms;

    TimeEnum(Integer ms) {
        this.ms = ms;
    }

    public Integer getMs() {
        return ms;
    }
}
