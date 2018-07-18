package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRoleEnum {

    ordinaryUser(0,"普通用户"),
    business(1,"商家"),
    yinshang(2,"银商");

    int code;
    String value;

    UserRoleEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
