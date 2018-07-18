package com.hengxunda.common.Enum;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * 订单类型枚举
 */
@Getter
public enum OrderTypeEnum {

    Buy(1,"买入"),
    Sell(0,"卖出");
    int code;
    String value;

    OrderTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    static final Map<Integer, OrderTypeEnum> cached = Maps.newLinkedHashMap();

    static {
        for (OrderTypeEnum code : OrderTypeEnum.values()) {
            cached.put(code.code, code);
        }

    }

    public static final OrderTypeEnum getEnum(int code) {
        return cached.get(code);
    }

}
