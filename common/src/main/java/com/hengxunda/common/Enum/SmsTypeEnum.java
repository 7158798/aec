package com.hengxunda.common.Enum;


import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

@Getter
public enum SmsTypeEnum {

    Register(0, "注册"),
    UpdateLoginPwd(1, "修改登录密码"),
    UpdatePayPwd(2, "修改支付密码"),
    Trans(3, "转账"),
    ChangePhone(4, "更换手机号"),
    ForgetLoginPassword(5, "忘记登录密码");

    int code;
    String value;

    SmsTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static final boolean codeQualified(Integer code) {
        if (code < Register.code || code > ForgetLoginPassword.code) {
            return true;
        }
        return false;
    }


    static Map<Integer,SmsTypeEnum> cached = Maps.newLinkedHashMap();

    static {
        for (SmsTypeEnum code : SmsTypeEnum.values()) {
            cached.put(Integer.valueOf(code.code), code);
        }
    }

    public static SmsTypeEnum getEnum(int code) {
        return cached.get(code);
    }


}
