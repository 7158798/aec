package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * msc释放状态
 */
@Getter
public enum MscRecordStatusEnum {
    undo(0,"未释放完成"),
    done(1,"已释放完成");

    Integer code;
    String value;

    MscRecordStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
