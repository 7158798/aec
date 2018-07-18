package com.hengxunda.generalservice.service.impl;

import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.entity.CoinRateEntity;
import com.hengxunda.common.entity.RateEntity;
import com.hengxunda.common.utils.Coin2CoinUtil;
import com.hengxunda.common.utils.TaxRateUtil;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.generalservice.service.ITaxRateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 法币汇率服务
 */
@Service
public class TaxRateServiceImpl implements ITaxRateService {

    @Autowired
    private IStringRedisService iStringRedisService;

    @Override
    public String cny2usd() {
        try {
            String money = iStringRedisService.get(WalletTypeEnum.USD.getCode());
            return queryRate(WalletTypeEnum.USD, money);
        } catch (Exception e) {
            return queryRate(WalletTypeEnum.USD, "");
        }
    }



    @Override
    public String cny2eur() {
        try {
            String money = iStringRedisService.get(WalletTypeEnum.EUR.getCode());
            return queryRate(WalletTypeEnum.EUR, money);
        } catch (Exception e) {
            return queryRate(WalletTypeEnum.EUR, "");
        }
    }

    @Override
    public String cny2hkd() {
        try {
            String money = iStringRedisService.get(WalletTypeEnum.HKD.getCode());
            return queryRate(WalletTypeEnum.HKD, money);
        } catch (Exception e) {
            return queryRate(WalletTypeEnum.HKD, "");
        }

    }

    @Override
    public String btc2usdt() {
        try {
            String money = iStringRedisService.get(TransPairEnum.BTC2USDT.getCode());
            return queryCoinRate(TransPairEnum.BTC2USDT, money);
        } catch (Exception e) {
            return queryCoinRate(TransPairEnum.BTC2USDT, "");
        }
    }



    @Override
    public String ltc2usdt() {
        try {
            String money = iStringRedisService.get(TransPairEnum.LTC2USDT.getCode());
            return queryCoinRate(TransPairEnum.LTC2USDT, money);
        } catch (Exception e) {
            return queryCoinRate(TransPairEnum.LTC2USDT, "");
        }
    }

    /**
     * 二次执行币币汇率查询操作
     * @param transPairEnum
     * @param money
     * @return
     */
    public String queryCoinRate(TransPairEnum transPairEnum, String money) {
        if (StringUtils.isNoneBlank(money)) {
            return money;
        }
        CoinRateEntity entity = null;
        switch (transPairEnum) {
            case LTC2USDT:
                entity = Coin2CoinUtil.ltc2usdt();
                break;
            case BTC2USDT:
                entity = Coin2CoinUtil.btc2usdt();
                break;
        }
        if (Objects.nonNull(entity) && StringUtils.isNoneBlank(entity.getClose().toString())) {
            iStringRedisService.set(transPairEnum.getCode(), entity.getClose().toString(), 10, TimeUnit.SECONDS);
            return entity.getClose().toString();
        }
        return "";
    }


    /**
     * 二次执行法币查询操作
     * @param typeEnum
     * @param money
     * @return
     */
    public String queryRate(WalletTypeEnum typeEnum, String money) {
        if (StringUtils.isNoneBlank(money)) {
            return money;
        }
        RateEntity entity = null;
        switch (typeEnum) {
            case USD:
                entity = TaxRateUtil.cny2usd();
                break;
            case EUR:
                entity = TaxRateUtil.cny2eur();
                break;
            case HKD:
                entity = TaxRateUtil.cny2hkd();
                break;
        }
        if (Objects.nonNull(entity) && StringUtils.isNoneBlank(entity.getMoney())) {
            iStringRedisService.set(typeEnum.getCode(), entity.getMoney(), 10, TimeUnit.MINUTES);
            return entity.getMoney();
        }
        return "";
    }
}
