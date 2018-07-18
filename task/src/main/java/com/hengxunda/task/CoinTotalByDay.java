package com.hengxunda.task;

import com.hengxunda.wallet.btc.BTCHelper;
import com.hengxunda.wallet.btc.LTCHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl
 * @Date: create in 2018/7/3
 */
@Slf4j
@Component
public class CoinTotalByDay {

    @Autowired
    private BTCHelper btcHelper;
    @Autowired
    private LTCHelper ltcHelper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 55 23 * * ? ")
    public void totalCoin(){
        log.info("统计钱包服务可用币总量任务开始");
        double btcBalance = 0;
        double ltcBalance = 0;
        try{
            btcBalance = btcHelper.getBalance();
            ltcBalance = ltcHelper.getBalance();
        }catch (Exception e){
            log.error("统计钱包服务可用币总量失败{}",e.getMessage());
        }
        log.info("查询结果：btc:{} , ltc:{}",btcBalance,ltcBalance);
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into t_wallet_system (");
        sqlBuffer.append("wallet_balance_aec, wallet_frozen_blance_aec, wallet_total_aec,");
        sqlBuffer.append("wallet_balance_msc, wallet_frozen_blance_msc, wallet_total_msc,");
        sqlBuffer.append("wallet_balance_ltc, wallet_frozen_balance_ltc, wallet_total_ltc,");
        sqlBuffer.append("wallet_balance_btc, wallet_frozen_balance_btc, wallet_total_btc, ");
        sqlBuffer.append("btc_total, ltc_total)");
        sqlBuffer.append("select ");
        sqlBuffer.append("a.wallet_balance_aec, a.wallet_frozen_blance_aec, a.wallet_total_aec, ");
        sqlBuffer.append("b.wallet_balance_msc, b.wallet_frozen_blance_msc, b.wallet_total_msc, ");
        sqlBuffer.append("c.wallet_balance_ltc, c.wallet_frozen_balance_ltc, c.wallet_total_ltc, ");
        sqlBuffer.append("d.wallet_balance_btc, d.wallet_frozen_balance_btc, d.wallet_total_btc, ");
        sqlBuffer.append(btcBalance).append(" btc_total, ").append(ltcBalance).append(" ltc_total ");
        sqlBuffer.append("from ");
        sqlBuffer.append("(select sum(balance) wallet_balance_aec, sum(frozen_blance) wallet_frozen_blance_aec, sum(balance + frozen_blance) wallet_total_aec, 0 _type from t_wallet_info where type = 'AEC' ) a left join ");
        sqlBuffer.append("(select sum(balance) wallet_balance_msc, sum(frozen_blance) wallet_frozen_blance_msc, sum(balance + frozen_blance) wallet_total_msc, 0 _type from t_wallet_info where type = 'MSC' ) b on a._type = b._type left join ");
        sqlBuffer.append("(select sum(balance) wallet_balance_ltc, sum(frozen_blance) wallet_frozen_balance_ltc, sum(balance + frozen_blance) wallet_total_ltc, 0 _type from t_wallet_info where type = 'LTC' ) c on a._type = c._type left join ");
        sqlBuffer.append("(select sum(balance) wallet_balance_btc, sum(frozen_blance) wallet_frozen_balance_btc, sum(balance + frozen_blance) wallet_total_btc, 0 _type from t_wallet_info where type = 'BTC' ) d on a._type = d._type ");
        log.info(sqlBuffer.toString());
        jdbcTemplate.execute(sqlBuffer.toString());

    }
}
