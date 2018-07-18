package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum  BringCoinLockEnum {

    CLOSE("0","关闭"),
    OPEN("1","打开");

    String code;
    String value;

    BringCoinLockEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
