package com.hengxunda.common.Enum;

public enum ShiroAuthEnum {

    UserNOTExist(StatusCodeEnum.CurrentUserNotExist.getCode(),"Current user does not exist"),
    TokenExpire(StatusCodeEnum.TokenExipred.getCode(),"token expire"),
    UserFrozen(StatusCodeEnum.CurrentUserFrozen.getCode(),"Current user is frozen");
    int code;
    String value;

    ShiroAuthEnum(int code, String value) {
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
