package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * 订单取消状态枚举
 */
@Getter
public enum OrderCancelStatusEnum {

    unCancel(0,"未发起取消"),
    cancel(1,"发起取消"),
    agree(2,"成功"),
    refuse(3,"失败");

    int code;
    String value;

    OrderCancelStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
