package com.hengxunda.task;

import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.entity.CoinRateEntity;
import com.hengxunda.common.utils.Coin2CoinUtil;
import com.hengxunda.generalservice.service.IStringRedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 查询火币网接口
 */
@Component
public class CoinRateRestRequestTask implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStringRedisService iStringRedisService;

    /**
     * 每隔5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void queryCoinTaxRate() {
        logger.info("================ start excute query coin rate schedule =================");
        CoinRateEntity entity = Coin2CoinUtil.btc2usdt();
        String money = entity == null ? "" : (StringUtils.isBlank(entity.getClose().toString()) ? "" : entity.getClose().toString());
        iStringRedisService.set(TransPairEnum.BTC2USDT.getCode(), money,10, TimeUnit.SECONDS);

        entity = Coin2CoinUtil.ltc2usdt();
        money = entity == null ? "" : (StringUtils.isBlank(entity.getClose().toString()) ? "" : entity.getClose().toString());
        iStringRedisService.set(TransPairEnum.LTC2USDT.getCode(), money,10, TimeUnit.SECONDS);

        logger.info("================end excute query coin rate schedule ==================");
    }

    @Override
    public void run(String... strings) throws Exception {
        queryCoinTaxRate();
    }
}
