package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.web.vo.*;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/13
 */
public interface IWalletService {

    WalletRecordListVo getWalletRecords(String coinName,String orderNo, String name, String phone,
                                        int type, String tradeRemark, Date createTime, Page page);

    WalletRechargeListVo getRechargeLogs(String coinName, String name, String phone, int type,
                                         int status, String remark, Date createTime, Page page);

    WalletExchangeListVo getExchangeLogs(String name, String phone, String type, Date createTime, Page page);

    WalletDialCoinListVo getDialCoinLogs(String coinName, String name, String phone,
                                         int type, int status, Date createTime, Page page);

    WalletTotalInfoListVo getWalletInfoByCoin(Page page);
}
