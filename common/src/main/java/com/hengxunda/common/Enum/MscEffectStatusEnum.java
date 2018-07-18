package com.hengxunda.common.Enum;


import lombok.Getter;

/**
 * MSC有效标识
 */
@Getter
public enum MscEffectStatusEnum {

    valid(0, "有效"),
    invalid(1, "无效");

    int code;
    String value;

    MscEffectStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
