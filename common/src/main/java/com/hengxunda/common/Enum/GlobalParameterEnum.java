package com.hengxunda.common.Enum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础参数表枚举
 */
@Getter
public enum GlobalParameterEnum {
    AEC2CNY("AEC/CNY","CNY兑换AEC比","basesetup"),
    MSC2CNY("MSC/CNY","CNY兑换MSC比","basesetup"),
    MSC2AEC("MSC/AEC","AEC兑换MSC比","basesetup"),

    ForeignCrurrencyExchange("foreign_crurrency_exchange","外币兑换折价率","basesetup"),

    AdvertMinAec("advert_min_aec","广告位AEC最低价","basesetup"),
    AdvertMaxAec("advert_max_aec","广告位AEC最高价","basesetup"),
    MinBond("min_bond","保证金最低额","basesetup"),

    WithdrawAec("withdraw_aec","aec提现手续费","withdraw"),
    WithdrawMsc("withdraw_msc","msc提现手续费","withdraw"),
    WithdrawBtc("withdraw_btc","btc提现手续费","withdraw"),
    WithdrawLtc("withdraw_ltc","ltc提现手续费","withdraw"),

    MscFrozeDay("msc_froze_day","MSC冻结设置冻结天数","mscset"),
    MscReleasePercent("msc_release_percent","MSC冻结设置释放百分比","mscset"),

    GenerationAwardAmount("generation_award_amount","代数奖励合格业绩","genaward"),
    GenerationAwardPercent("generation_award_percent","代数奖励比例","genaward"),

    MscIncreaseSplit("msc_increase_split", "MSC增长间隔","basesetup"),
    MscIncreasePrice("msc_increase_price", "MSC增长价格","basesetup"),
    RestMscAmount("rest_msc_amount","MSC价格增长剩余基数","basesetup"),

    BtcExchangePercent("btc_exchange_percent", "BTC兑换折价率（%）","basesetup"),
    LtcExchangePercent("ltc_exchange_percent", "LTC兑换折价率（%）","basesetup"),

    AecWithdrawFeeAmount("aec_withdraw_fee_amount", "AEC提现手续费金额","aecwithdraw"),
    AecWithdrawFeePercent("aec_withdraw_fee_percent", "AEC提现手续费百分比数","aecwithdraw"),
    AecWithdrawFeeRule("aec_withdraw_fee_rule", "AEC提现手续费规则(1金额,2百分百,3金额+百分比)","aecwithdraw"),

    BtcWithdrawFeeAmount("btc_withdraw_fee_amount", "BTC提现手续费金额","btcwithdraw"),
    BtcWithdrawFeePercent("btc_withdraw_fee_percent", "BTC提现手续费百分比数","btcwithdraw"),
    BtcWithdrawFeeRule("btc_withdraw_fee_rule", "BTC提现手续费规则(1金额,2百分百,3金额+百分比)","btcwithdraw"),

    MscWithdrawFeeAmount("msc_withdraw_fee_amount", "MSC提现手续费金额","mscwithdraw"),
    MscWithdrawFeePercent("msc_withdraw_fee_percent", "MSC提现手续费百分比数","mscwithdraw"),
    MscWithdrawFeeRule("msc_withdraw_fee_rule", "MSC提现手续费规则(1金额,2百分百,3金额+百分比)","mscwithdraw"),

    LtcWithdrawFeeAmount("ltc_withdraw_fee_amount", "LTC提现手续费金额","ltcwithdraw"),
    LtcWithdrawFeePercent("ltc_withdraw_fee_percent", "LTC提现手续费百分比数","ltcwithdraw"),
    LtcWithdrawFeeRule("ltc_withdraw_fee_rule", "LTC提现手续费规则(1金额,2百分百,3金额+百分比)","ltcwithdraw"),

    PaymentFeeAmount("payment_fee_amount", "支付手续费金额","paymentfee"),
    PaymentFeePercent("payment_fee_percent", "支付手续费百分比数","paymentfee"),
    PaymentFeeRule("payment_fee_rule", "支付手续费规则(1金额,2百分百,3金额+百分比)","paymentfee"),

    C2CFeeAmount("c2c_fee_amount", "C2C手续费金额","c2cfee"),
    C2CFeePercent("c2c_fee_percent", "C2C手续费百分比数","c2cfee"),
    C2CFeeRule("c2c_fee_rule", "C2C手续费规则(1金额,2百分百,3金额+百分比)","c2cfee"),

    GenerationRewardSwitch("generation_reward_switch","代数奖励开关","switch"),
    LevelRewardSwitch("level_reward_switch","级差奖励开关","switch"),
    WithdrawSwitch("withdraw_switch","提现开关","switch"),

    ALIYUN_ENDPOINT("aliyunEndPoint","阿里云接入点","oss"),
    ALIYUN_BUKETNAME("aliyunBuketName","名称","oss"),
    ALIYUN_ACCESS_KEY_ID("aliyunAccessKeyID","接入keyId","oss"),
    ALIYUN_ACCESS_KEY_SECRET("aliyunAccessKeySecret","接入密钥","oss");



    String code;
    String value;
    String group;

    private static final Map<String, GlobalParameterEnum> MAP = new HashMap<>();

    GlobalParameterEnum(String code, String value,String group) {
        this.code = code;
        this.value = value;
        this.group = group;
    }

    public static GlobalParameterEnum getByCode(String code) {
        if(MAP.size() == 0) {
            for (GlobalParameterEnum globalParameterEnum : GlobalParameterEnum.values()) {
                MAP.put(globalParameterEnum.getCode(), globalParameterEnum);
            }
        }

        return MAP.get(code);
    }
}
