package com.hengxunda.task;

import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.entity.RateEntity;
import com.hengxunda.common.utils.TaxRateUtil;
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
 * 定时查询法币汇率
 */
@Component
public class TaxRateTask implements CommandLineRunner{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStringRedisService iStringRedisService;

    /**
     * 每隔5分钟执行一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void queryTaxRate() {
        logger.info("================ start excute query tax rate schedule =================");
        RateEntity entity = TaxRateUtil.cny2usd();
        String money = entity == null ? "" : (StringUtils.isBlank(entity.getMoney()) ? "" : entity.getMoney());
        iStringRedisService.set(WalletTypeEnum.USD.getCode(), money,10, TimeUnit.MINUTES);

        entity = TaxRateUtil.cny2eur();
        money = entity == null ? "" : (StringUtils.isBlank(entity.getMoney()) ? "" : entity.getMoney());
        iStringRedisService.set(WalletTypeEnum.EUR.getCode(), money,10, TimeUnit.MINUTES);

        entity = TaxRateUtil.cny2hkd();
        money = entity == null ? "" : (StringUtils.isBlank(entity.getMoney()) ? "" : entity.getMoney());
        iStringRedisService.set(WalletTypeEnum.HKD.getCode(), money,10, TimeUnit.MINUTES);
        logger.info("================end excute query tax rate schedule ==================");
    }

    @Override
    public void run(String... strings) throws Exception {
        queryTaxRate();
    }
}
