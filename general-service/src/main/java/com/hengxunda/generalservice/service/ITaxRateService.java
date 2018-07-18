package com.hengxunda.generalservice.service;

/**
 * 法币汇率服务
 */
public interface ITaxRateService {

    /**
     *人民币对USD汇率
     * @return
     */
    String cny2usd();

    /**
     *人民币对EUR汇率
     * @return
     */
    String cny2eur();

    /**
     *人民币对HKD汇率
     * @return
     */
    String cny2hkd();

    /**
     * BTC对USDT汇率
     */
    String btc2usdt();

    /**
     * LTC对USDT汇率
     */
    String ltc2usdt();
}
