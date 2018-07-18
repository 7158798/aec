package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum PlatformEnum {

    pc(0,"pc端"),
    app(1,"普通用户app"),
    wapp(2,"银商app");

    int code;
    String value;

    PlatformEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
