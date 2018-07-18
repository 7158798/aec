package com.hengxunda.common.Enum;

/**
 * 用户状态枚举
 */
public enum UserStatusEnum {

    Forzen(1,"冻结"),
    Effective(0,"有效");

    int code;
    String value;

    UserStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
