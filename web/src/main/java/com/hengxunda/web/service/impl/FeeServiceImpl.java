package com.hengxunda.web.service.impl;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.mapper_custom.FeeMapper;
import com.hengxunda.dao.po.web.FeeStatisticsPo;
import com.hengxunda.dao.po.web.TimePo;
import com.hengxunda.dao.po.web.WalletRecordFeePo;
import com.hengxunda.dao.po.web.WithdrawFeePo;
import com.hengxunda.web.service.IFeeService;
import com.hengxunda.web.vo.FeeStatisticsListVo;
import com.hengxunda.web.vo.WalletRecordFeeListVo;
import com.hengxunda.web.vo.WithdrawFeeListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Slf4j
@Service
public class FeeServiceImpl implements IFeeService {


    @Autowired
    private FeeMapper feeMapper;

    /**
     * 手续费统计
     * */
    @Override
    public FeeStatisticsListVo getFeeStatistics(Date date, Page page) {

        TimePo timePo = new TimePo();
        if(date!=null){
            timePo.setBeginTime(date).setEndTime(getEndTime(date));
        }

        List<FeeStatisticsPo> list = feeMapper.getFeeStatistics(timePo, new Page(page.getPageNo(), page.getLimit()));
        int count = feeMapper.countFeeStatistics(timePo);
        FeeStatisticsListVo feeStatisticsListVo = new FeeStatisticsListVo();
        feeStatisticsListVo.setFeeStatistics(list).setTotal(count);
        return feeStatisticsListVo;
    }

    /**
     * 支付手续费
     * */
    @Override
    public WalletRecordFeeListVo getPayFees(String orderNo, String fromName, String fromPhone, String toName, String toPhone, Date createTime, Page page) {

        WalletRecordFeePo walletRecordFeePo = getWalletFeePo(orderNo,fromName,fromPhone,toName,toPhone,createTime);
        List<WalletRecordFeePo> list = feeMapper.getPayFees(walletRecordFeePo, new Page(page.getPageNo(),page.getLimit()));
        int count = feeMapper.countPayFees(walletRecordFeePo);
        WalletRecordFeeListVo walletRecordFeeListVo = new WalletRecordFeeListVo();
        walletRecordFeeListVo.setWalletRecordFees(list).setTotal(count);

        return walletRecordFeeListVo;
    }

    /**
     * C2C手续费
     * */
    @Override
    public WalletRecordFeeListVo getCtcFees(String orderNo, String fromName, String fromPhone, String toName, String toPhone, Date createTime, Page page) {
        WalletRecordFeePo walletRecordFeePo = getWalletFeePo(orderNo,fromName,fromPhone,toName,toPhone,createTime);
        List<WalletRecordFeePo> list = feeMapper.getCtcFees(walletRecordFeePo,new Page(page.getPageNo(), page.getLimit()));
        int count = feeMapper.countCtcFees(walletRecordFeePo);
        WalletRecordFeeListVo walletRecordFeeListVo = new WalletRecordFeeListVo();
        walletRecordFeeListVo.setWalletRecordFees(list).setTotal(count);
        return walletRecordFeeListVo;
    }

    /**
     * 提现手续费
     * */
    @Override
    public WithdrawFeeListVo getWithdrawFees(String orderNo, String name, String phone, String coin, String address, Date createTime, Page page) {

        WithdrawFeePo withdrawFeePo = new WithdrawFeePo();
        if(orderNo != null&& !orderNo.equals("")){
            withdrawFeePo.setOrderNo(orderNo);
        }
        if(name != null&& !name.equals("")){
            withdrawFeePo.setName(name);
        }
        if(phone != null&& !phone.equals("")){
            withdrawFeePo.setPhone(phone);
        }
        if(coin != null&& !coin.equals("")){
            withdrawFeePo.setCoin(coin);
        }
        if(address != null&& !address.equals("")){
            withdrawFeePo.setAddress(address);
        }
        if(createTime!=null){
            withdrawFeePo.setBeginTime(createTime).setEndTime(getEndTime(createTime));
        }
        List<WithdrawFeePo> withdrawFeePos = feeMapper.getWithdrawFees(withdrawFeePo, new Page(page.getPageNo(), page.getLimit()));
        int count  = feeMapper.countWithdrawFees(withdrawFeePo);
        WithdrawFeeListVo withdrawFeeListVo = new WithdrawFeeListVo();
        withdrawFeeListVo.setWithdrawFeePos(withdrawFeePos).setTotal(count);
        return withdrawFeeListVo;
    }





    //获取实例
    private WalletRecordFeePo getWalletFeePo(String orderNo, String fromName, String fromPhone, String toName, String toPhone,Date creatTime){
        WalletRecordFeePo walletRecordFeePo = new WalletRecordFeePo();
        if(orderNo != null&& !orderNo.equals("")){
            walletRecordFeePo.setOrderNo(orderNo);
        }
        if(fromName != null&& !fromName.equals("")){
            walletRecordFeePo.setFromName(fromName);
        }
        if(fromPhone != null&& !fromPhone.equals("")){
            walletRecordFeePo.setFromPhone(fromPhone);
        }
        if(toName != null&& !toName.equals("")){
            walletRecordFeePo.setToName(toName);
        }
        if(toPhone != null&& !toPhone.equals("")){
            walletRecordFeePo.setToPhone(toPhone);
        }
        if(creatTime!=null){
            walletRecordFeePo.setBeginTime(creatTime).setEndTime(getEndTime(creatTime));
        }
        return walletRecordFeePo;
    }

    private Date getEndTime(Date beginTime){
        Calendar c = Calendar.getInstance();
        c.setTime(beginTime);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

//    @Override
//    public List<FeeStatisticsPo> downloadFeeStatistics(Date beginTime, Date endTime) {
//
//        TimePo timePo = new TimePo();
//        timePo.setBeginTime(beginTime).setEndTime(endTime);
//
//        return feeMapper.downloadFeeStatistics(timePo);
//    }



}
