package com.hengxunda.common.Enum;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 银行卡类型
 */
@Getter
public enum BankTypeEnum {
    BANK_NO(0, "银行卡"),
    PAYPAL(1, "Paypal"),
    WESTERN_UNION(2, "西联汇款"),
    SWIFT(3, "SWIFT国际汇款"),
    ALIPAY(4, "支付宝");

    int code;
    String value;

    BankTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static BankTypeEnum acquireByCode(int code) {
        return Arrays.stream(BankTypeEnum.values()).filter(v -> Objects.equals(v.code, code)).findFirst()
                .orElse(BankTypeEnum.BANK_NO);
    }

    public static String getValue(BankTypeEnum bankTypeEnum) {
        return acquireByCode(bankTypeEnum.code).value;
    }

}