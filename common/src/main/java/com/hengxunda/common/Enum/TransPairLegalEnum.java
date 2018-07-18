package com.hengxunda.common.Enum;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * 法币交易对枚举
 */
@Getter
public enum TransPairLegalEnum {

    AEC2CNY("AEC/CNY", "CNY购买AEC"),
    AEC2USD("AEC/USD", "USD购买AEC"),
    AEC2EUR("AEC/EUR", "EUR购买AEC"),
    AEC2HKD("AEC/HKD", "HKD购买AEC");

    static final Map<String, TransPairLegalEnum> cached = Maps.newLinkedHashMap();

    static {
        for (TransPairLegalEnum code : TransPairLegalEnum.values()) {
            cached.put(code.code, code);
        }

    }

    String code;
    String value;

    TransPairLegalEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static final TransPairLegalEnum getEnum(String code) {

        return cached.get(code);
    }
}
