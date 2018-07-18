package com.hengxunda.common.Enum;


import lombok.Getter;

/**
 * msc 分红状态常量
 */
@Getter
public enum MscProfitStatusEnum {

    undo(0, "未分红"),
    done(1, "已分红");

    int code;
    String value;

    MscProfitStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
