package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.FeeStatisticsPo;
import com.hengxunda.dao.po.web.TimePo;
import com.hengxunda.dao.po.web.WalletRecordFeePo;
import com.hengxunda.dao.po.web.WithdrawFeePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
public interface FeeMapper {

    /**
    * 手续费总览
    * */
    List<FeeStatisticsPo> getFeeStatistics(@Param("record")TimePo record, @Param("page")Page page);

    int countFeeStatistics(@Param("record")TimePo record);

    List<FeeStatisticsPo> downloadFeeStatistics(@Param("record")TimePo record);

    /**
    * 支付手续费
    * */
    List<WalletRecordFeePo> getPayFees(@Param("record")WalletRecordFeePo record, @Param("page") Page page);

    int countPayFees(@Param("record")WalletRecordFeePo record);

    List<WalletRecordFeePo> downloadPayFees(@Param("record")WalletRecordFeePo record);

    /**
     * C2C手续费
     * */
    List<WalletRecordFeePo> getCtcFees(@Param("record")WalletRecordFeePo record, @Param("page") Page page);

    int countCtcFees(@Param("record")WalletRecordFeePo record);

    List<WalletRecordFeePo> downloadCtcFees(@Param("record")WalletRecordFeePo record);

    /**
    * 提现手续费
    * */
    List<WithdrawFeePo> getWithdrawFees(@Param("record") WithdrawFeePo record, @Param("page")Page page);

    int countWithdrawFees(@Param("record") WithdrawFeePo record);

    List<WithdrawFeePo> downloadWithdrawFees(@Param("record") WithdrawFeePo record);

}
