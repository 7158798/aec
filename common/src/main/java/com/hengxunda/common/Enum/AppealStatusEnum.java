package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum AppealStatusEnum {

    UNTREATED(0,"未处理"),
    ARBITRATION(1,"仲裁中"),
    Recall(2,"撤回申诉"),
    BUY_WIN(3,"买家胜出"),
    SELL_WIN(4,"卖家胜出");


    int code;
    String value;

    AppealStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
