package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
public enum OrderStatusEnum {

    unpaid(0,"未支付"),
    paid(1,"已付款"),
    appealing(2,"申诉中"),
    canceled(3,"已取消"),
    completed(4,"已完成");

    int code;
    String value;

    OrderStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
