package com.hengxunda.common.Enum;

import lombok.Getter;

@Getter
public enum WithdrawFeeEnum {

    c2cfee("c2cfee","c2c手续费"),
    c2c_fee_rule("c2c_fee_rule","C2C手续费规则(1金额,2百分百,3金额+百分比)"),
    c2c_fee_amount("c2c_fee_amount","C2C手续费金额"),
    c2c_fee_percent("c2c_fee_percent","C2C手续费百分比数");




    String code;
    String value;

    WithdrawFeeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
