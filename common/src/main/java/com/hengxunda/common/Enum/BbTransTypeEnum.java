package com.hengxunda.common.Enum;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * 币币交易类型枚举
 */
@Getter
public enum BbTransTypeEnum {

    Buy(1,"买入"),
    Sell(0,"卖出");
    int code;
    String value;

    BbTransTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    static final Map<Integer, BbTransTypeEnum> cached = Maps.newLinkedHashMap();

    static {
        for (BbTransTypeEnum code : BbTransTypeEnum.values()) {
            cached.put(code.code, code);
        }

    }

    public static final BbTransTypeEnum getEnum(int code) {
        return cached.get(code);
    }

}
