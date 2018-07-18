package com.hengxunda.common.Enum;

/**
 * 验证码状态枚举
 */
public enum VerificatonCodeStatusEnum {

    NotRead(0,"未使用"),
    Readed(1,"已使用");
    int code;
    String value;

    VerificatonCodeStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
