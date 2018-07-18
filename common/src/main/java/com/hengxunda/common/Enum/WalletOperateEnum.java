package com.hengxunda.common.Enum;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 钱包操作枚举
 */
@Getter
public enum WalletOperateEnum {

    zero(0,"平台拨币"),
    one(1,"充币"),
    two(2,"提币"),
    three(3,"转账"),
    four(4,"c2c转账"),
    five(5,"AEC兑换MSC"),
    six(6,"冻结"),
    seven(7,"解冻"),
    eight(8,"提高保证金"),
    nine(9,"降低保证金"),
    ten(10,"差额奖励"),
    eleven(11,"msc释放"),
    twelve(12,"股权分红"),
    thirty(13,"代数奖励"),
    fourteen(14,"商户付款"),
    fifteen(15,"平台扣币"),
    sixteen(16,"提取保证金"),
    seventeen(17,"成为银商"),
    eigthteen(18,"BTC兑换AEC"),
    nineteen(19,"LTC兑换AEC"),
    twenty(20,"币币交易");

    int code;
    String value;

    WalletOperateEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static WalletOperateEnum acquireByCode(int code) {
        return Arrays.stream(WalletOperateEnum.values()).filter(v -> Objects.equals(v.code, code)).findFirst()
                .orElse(WalletOperateEnum.zero);
    }

    public static String getValue(WalletOperateEnum bankTypeEnum) {
        return acquireByCode(bankTypeEnum.code).value;
    }

    public String getValue() {
        return value;
    }
}
