package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum  AdvertStatusEnum {

    upShelf(0,"上架"),
    downShelf(1,"下架");

    int code;
    String value;

    AdvertStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
