package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum AdvertTypeEnum {

    Sell(0,"卖出"),
    Buy(1,"买入");

    int code;
    String value;

     AdvertTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
