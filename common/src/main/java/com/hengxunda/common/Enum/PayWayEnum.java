package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * 支付方式
 */

@Getter
public enum PayWayEnum {
    AEC_PAY("0", "AEC扫一扫普通付款"),
    MERCHANT_AEC_PAY("1", "AEC扫一扫商家付款"),
    MERCHANT_MSC_PAY("2", "MSC扫一扫商家付款");

    String code;
    String value;


    PayWayEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
