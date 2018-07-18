package com.hengxunda.common.Enum;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 钱包类型
 */
@Getter
public enum WalletTypeEnum {

    BTC("BTC","比特币"),
    LTC("LTC","莱特币"),
    ETH("ETH","以太坊"),
    AEC("AEC","以太坊代币"),
    MSC("MSC","以太坊代币"),
    CNY("CNY","人民币"),
    USD("USD","美元"),
    EUR("EUR","欧元"),
    HKD("HKD","港币");

    String code;
    String value;

    WalletTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static WalletTypeEnum acquireByCode(String code) {
        return Arrays.stream(WalletTypeEnum.values()).filter(v -> Objects.equals(v.getCode(), code)).findFirst()
                .orElse(WalletTypeEnum.AEC);

    }
}
