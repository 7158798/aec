package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.FeeStatisticsPo;
import com.hengxunda.dao.po.web.WalletRecordFeePo;
import com.hengxunda.dao.po.web.WithdrawFeePo;
import com.hengxunda.web.vo.FeeStatisticsListVo;
import com.hengxunda.web.vo.WalletRecordFeeListVo;
import com.hengxunda.web.vo.WithdrawFeeListVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
public interface IFeeService {

    FeeStatisticsListVo getFeeStatistics(Date date,Page page);

//    List<FeeStatisticsPo> downloadFeeStatistics(Date beginTime, Date endTime);

    WalletRecordFeeListVo getPayFees(String orderNo,String fromName, String fromPhone,
                                     String toName, String toPhone, Date createTime, Page page);

//    List<WalletRecordFeePo> downloadPayFees();

    WalletRecordFeeListVo getCtcFees(String orderNo,String fromName, String fromPhone,
                                     String toName, String toPhone, Date createTime, Page page);

//    List<WalletRecordFeePo> downloadCtcFees();

    WithdrawFeeListVo getWithdrawFees(String orderNo,String name, String phone,
                                      String coin, String address, Date createTime, Page page);

//    List<WithdrawFeePo> downloadWithdraw();



}
