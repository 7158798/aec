package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum OrderAppealStatusEnum {

    untreated(0,"未处理"),
    arbitrament(1,"申诉中"),
    win(2,"胜出"),
    lose(3,"失败"),
    recall(4,"申诉撤回");

    int code;
    String value;

    OrderAppealStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
