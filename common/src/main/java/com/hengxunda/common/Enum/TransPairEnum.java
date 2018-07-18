package com.hengxunda.common.Enum;

import lombok.Getter;

/**
 * 平台交易对枚举
 *
 */
@Getter
public enum TransPairEnum {
    BTC2AEC("BTC/AEC","AEC购买BTC"),
    L2AEC("LTC/AEC","AEC购买LTC"),
    AEC2BTC("AEC/BTC","AEC购买BTC"),
    AEC2LTC("AEC/LTC","AEC购买LTC"),
    MSC2AEC("MSC/AEC","AEC购买MSC"),
    AEC2MSC("AEC/MSC","MSC购买AEC"),
    AEC2AEC("AEC/AEC","AEC购买AEC"),
    MSC2MSC("MSC/MSC","MSC购买MSC"),
    BTC2BTC("BTC/BTC","BTC购买BTC"),
    LTC2LTC("LTC/LTC","LTC购买LTC"),
    BTC2USDT("btcusdt","USDT购买BTC"),
    LTC2USDT("ltcusdt","USDT购买BTC");
    String code;
    String value;

    TransPairEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
