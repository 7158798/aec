package com.hengxunda.web.service.impl;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.mapper_custom.WalletWebCustomMapper;
import com.hengxunda.dao.po.web.*;
import com.hengxunda.wallet.btc.BTCHelper;
import com.hengxunda.wallet.btc.LTCHelper;
import com.hengxunda.web.service.IWalletService;
import com.hengxunda.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/13
 */
@Slf4j
@Service
public class WalletServiceImpl implements IWalletService {

    @Autowired
    private WalletWebCustomMapper walletWebCustomMapper;

    @Autowired
    private BTCHelper btcHelper;

    @Autowired
    private LTCHelper ltcHelper;

    /**
    * 钱包明细
    * */
    @Override
    public WalletRecordListVo getWalletRecords(String coinName, String orderNo, String name, String phone, int type, String tradeRemark, Date createTime, Page page) {

        WalletRecordPo walletRecordPo = new WalletRecordPo();
        walletRecordPo.setCoinName("%"+coinName+"%").setType(type);

        if(orderNo != null&& !orderNo.equals("")){
            walletRecordPo.setOrderNo(orderNo);
        }
        if(name != null&& !name.equals("")){
            walletRecordPo.setName(name);
        }
        if(phone != null&& !phone.equals("")){
            walletRecordPo.setPhone(phone);
        }
        if(tradeRemark != null&& !tradeRemark.equals("")){
            walletRecordPo.setTradeRemark(tradeRemark);
        }
        if(createTime != null&& !createTime.equals("")){
            walletRecordPo.setCreateTime(createTime);
        }
        List<WalletRecordPo> walletRecordPos = walletWebCustomMapper.getWalletRecords(walletRecordPo,new Page(page.getPageNo(),page.getLimit()));
        int count = walletWebCustomMapper.countWalletRecords(walletRecordPo);
        WalletRecordListVo walletRecordListVo = new WalletRecordListVo();
        walletRecordListVo.setWalletRecordPos(walletRecordPos).setTotal(count);

        return walletRecordListVo;
    }

    /**
     * 提充明细
     * */
    @Override
    public WalletRechargeListVo getRechargeLogs(String coinName, String name, String phone, int type, int status, String remark, Date createTime, Page page) {

        WalletLog walletLog = new WalletLog();
        walletLog.setCoinName("%"+coinName+"%").setType(type).setStatus(status);

        if(name != null&& !name.equals("")){
            walletLog.setName(name);
        }
        if(phone != null&& !phone.equals("")){
            walletLog.setPhone(phone);
        }
        if(remark != null&& !remark.equals("")){
            walletLog.setRemark(remark);
        }
        if(createTime != null&& !createTime.equals("")){
            walletLog.setCreateTime(createTime);
        }
        List<WalletLog> walletLogs = walletWebCustomMapper.getRechargeLogs(walletLog,new Page(page.getPageNo(),page.getLimit()));
        int count = walletWebCustomMapper.countRechargeLogs(walletLog);
        WalletRechargeListVo walletRechargeListVo = new WalletRechargeListVo();
        walletRechargeListVo.setWalletLogs(walletLogs).setTotal(count);

        return walletRechargeListVo;
    }

    /**
     * 兑换明细
     * */
    @Override
    public WalletExchangeListVo getExchangeLogs(String name, String phone, String type, Date createTime, Page page) {

        WalletExchangePo walletExchangePo = new WalletExchangePo();

        if(name != null&& !name.equals("")){
            walletExchangePo.setName(name);
        }
        if(phone != null&& !phone.equals("")){
            walletExchangePo.setPhone(phone);
        }
        if(type != null&& !type.equals("")){
            walletExchangePo.setType(type);
        }
        if(createTime != null&& !createTime.equals("")){
            walletExchangePo.setCreateTime(createTime);
        }
        List<WalletExchangePo> walletExchangePos = walletWebCustomMapper.getExchangeLogs(walletExchangePo,new Page(page.getPageNo(),page.getLimit()));
        int count = walletWebCustomMapper.countExchangeLogs(walletExchangePo);
        WalletExchangeListVo walletExchangeListVo = new WalletExchangeListVo();
        walletExchangeListVo.setWalletExchangePos(walletExchangePos).setTotal(count);

        return walletExchangeListVo;
    }

    /**
     * 拨币明细
     * */
    @Override
    public WalletDialCoinListVo getDialCoinLogs(String coinName, String name, String phone, int type, int status, Date createTime, Page page) {

        DialCoinPo dialCoinPo = new DialCoinPo();
        dialCoinPo.setStatus(status).setType(type);

        if(coinName != null&& !coinName.equals("")){
            dialCoinPo.setCoin(coinName);
        }
        if(name != null&& !name.equals("")){
            dialCoinPo.setName(name);
        }
        if(phone != null&& !phone.equals("")){
            dialCoinPo.setPhone(phone);
        }
        if(createTime != null&& !createTime.equals("")){
            dialCoinPo.setCreateTime(createTime);
        }
        List<DialCoinPo> dialCoinPos = walletWebCustomMapper.getDialCoinLogs(dialCoinPo,new Page(page.getPageNo(),page.getLimit()));
        int count = walletWebCustomMapper.countDialCoinLogs(dialCoinPo);
        WalletDialCoinListVo walletDialCoinListVo = new WalletDialCoinListVo();
        walletDialCoinListVo.setDialCoinPos(dialCoinPos).setTotal(count);

        return walletDialCoinListVo;
    }

    /**
    * 钱包系统
    * */
    @Override
    public WalletTotalInfoListVo getWalletInfoByCoin(Page page) {
        List<WalletTotalInfoPo> walletTotalInfoPos =  walletWebCustomMapper.getWalletTotalInfo(new Page(page.getPageNo(),page.getLimit()));
        int count = walletWebCustomMapper.countWalletTotalInfo();
        double btc = 0;
        double ltc = 0;
        try{
            btc = btcHelper.getBalance();
            ltc = ltcHelper.getBalance();
        }catch (Exception e){
            log.error("查询钱包服务节点总额失败{}",e.getMessage());
        }
        walletTotalInfoPos.get(0).setServerTotalBTC(btc).setServerTotalLTC(ltc);
        WalletTotalInfoListVo walletTotalInfoListVo = new WalletTotalInfoListVo();
        walletTotalInfoListVo.setWalletTotalInfoPos(walletTotalInfoPos).setTotal(count);
        return walletTotalInfoListVo;
    }
}
