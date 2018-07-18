package com.hengxunda.common.utils;

import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.entity.CoinRateEntity;
import com.hengxunda.common.entity.CoinRateList;
import org.apache.commons.lang3.StringUtils;

/**
 * 火币网币币交易汇率查询
 */
public class Coin2CoinUtil {
    private static String url = "https://api.huobipro.com/market/detail?symbol=%s";

    private static String send(String url, String symbol) {
        url = String.format(url,symbol);
        return HttpUtils.get(url);
    }

    /**
     * usdt兑换btc
     * @return
     */
    public static CoinRateEntity btc2usdt(){
        String result = send(url, TransPairEnum.BTC2USDT.getCode());
        if(StringUtils.isBlank(result)){
            return null;
        }
        CoinRateList coinRateList = GsonUtils.getGson().fromJson(result,CoinRateList.class);
        return coinRateList.getTick();
    }

    /**
     * usdt兑换ltc
     * @return
     */
    public static CoinRateEntity ltc2usdt(){
        String result = send(url, TransPairEnum.LTC2USDT.getCode());
        if(StringUtils.isBlank(result)){
            return null;
        }
        CoinRateList coinRateList = GsonUtils.getGson().fromJson(result,CoinRateList.class);
        return coinRateList.getTick();
    }

    public static void main(String[] args) {
        System.out.println(ltc2usdt().getClose());
    }
}
