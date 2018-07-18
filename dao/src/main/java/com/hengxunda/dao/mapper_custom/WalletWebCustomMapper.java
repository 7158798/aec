package com.hengxunda.dao.mapper_custom;


import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Author: lsl
 * @Date: create in 2018/6/14
 */
public interface WalletWebCustomMapper {

    /**
    * 钱包明细
    * */
    List<WalletRecordPo> getWalletRecords(@Param("record")WalletRecordPo record, @Param("page")Page page);

    int countWalletRecords(@Param("record")WalletRecordPo record);

    /**
    * 提充明细
    * */
    List<WalletLog> getRechargeLogs(@Param("record")WalletLog record, @Param("page")Page page);

    int countRechargeLogs(@Param("record")WalletLog record);

    /**
     *
    * 兑换明细
    * */
    List<WalletExchangePo> getExchangeLogs(@Param("record")WalletExchangePo record, @Param("page")Page page);

    int countExchangeLogs(@Param("record")WalletExchangePo record);

    /**
    * 拨币明细
    * */
    List<DialCoinPo> getDialCoinLogs(@Param("record")DialCoinPo record, @Param("page")Page page);

    int countDialCoinLogs(@Param("record")DialCoinPo record);

    /**
    * 获取系统内用户持币统计
    * \*/
    List<WalletTotalInfoPo> getWalletTotalInfo(@Param("page")Page page);

    int countWalletTotalInfo();


}