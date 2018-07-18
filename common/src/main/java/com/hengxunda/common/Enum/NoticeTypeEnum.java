package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum NoticeTypeEnum {

    news("1","消息"),
    notice("2","通知"),
    proclamation("3","公告");

    String  code;
    String value;

    NoticeTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
