package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.web.service.IFeeService;
import com.hengxunda.web.vo.FeeStatisticsListVo;
import com.hengxunda.web.vo.WalletRecordFeeListVo;
import com.hengxunda.web.vo.WithdrawFeeListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Slf4j
@Api(description = "手续费相关")
@RestController
@RequestMapping("/fee")
public class FeeController {


    @Autowired
    private IFeeService feeService;


    @ApiOperation("手续费按日统计")
    @PostMapping("/statistics")
    public CommonResponse<FeeStatisticsListVo> getFeeStatistics(
            @ApiParam(value = "选择日期当天0时0分0秒")@RequestParam(required = false)Date createTime,
            Page page){

        FeeStatisticsListVo feeStatisticsListVo = feeService.getFeeStatistics(createTime, page);
        return CommonResponse.ok(feeStatisticsListVo);
    }

    @ApiOperation("支付手续费明细")
    @PostMapping("/pay")
    public CommonResponse<WalletRecordFeeListVo> getPayFees(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false)String orderNo,
            @ApiParam(value = "支付者姓名，非必传")@RequestParam(required = false)String fromName,
            @ApiParam(value = "支付者手机，非必传")@RequestParam(required = false)String fromPhone,
            @ApiParam(value = "接受者姓名，非必传")@RequestParam(required = false)String toName,
            @ApiParam(value = "接受者手机，非必传")@RequestParam(required = false)String toPhone,
            @ApiParam(value = "选择日期当天0时0分0秒")@RequestParam(required = false)Date createTime,
            Page page){

        WalletRecordFeeListVo walletRecordFeeListVo = feeService.getPayFees(orderNo,fromName,fromPhone,toName,toPhone,createTime, page);
        return CommonResponse.ok(walletRecordFeeListVo);
    }

    @ApiOperation("C2C手续费明细")
    @PostMapping("/ctc")
    public CommonResponse<WalletRecordFeeListVo> getCtcFees(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false)String orderNo,
            @ApiParam(value = "支付者姓名，非必传")@RequestParam(required = false)String fromName,
            @ApiParam(value = "支付者手机，非必传")@RequestParam(required = false)String fromPhone,
            @ApiParam(value = "接受者姓名，非必传")@RequestParam(required = false)String toName,
            @ApiParam(value = "接受者手机，非必传")@RequestParam(required = false)String toPhone,
            @ApiParam(value = "选择日期当天0时0分0秒")@RequestParam(required = false)Date createTime,
            Page page){

        WalletRecordFeeListVo walletRecordFeeListVo = feeService.getCtcFees(orderNo,fromName,fromPhone,toName,toPhone,createTime, page);
        return CommonResponse.ok(walletRecordFeeListVo);
    }

    @ApiOperation("提现手续费明细")
    @PostMapping("/withdraw")
    public CommonResponse<WithdrawFeeListVo> getWithdrawFees(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false)String orderNo,
            @ApiParam(value = "提现人姓名，非必传")@RequestParam(required = false)String fromName,
            @ApiParam(value = "提现人手机，非必传")@RequestParam(required = false)String fromPhone,
            @ApiParam(value = "提现币种，非必传（AEC,MSC,BTC,LTC）")@RequestParam(required = false)String coin,
            @ApiParam(value = "提现地址，非必传")@RequestParam(required = false)String address,
            @ApiParam(value = "选择日期当天0时0分0秒")@RequestParam(required = false)Date createTime,
            Page page){

        if( coin != null && !coin.equals("")){
            coin = coin.toUpperCase();
            coin = coin+"/"+coin;
        }
        WithdrawFeeListVo withdrawFeeListVo = feeService.getWithdrawFees(orderNo,fromName,fromPhone,coin,address,createTime, page);
        return CommonResponse.ok(withdrawFeeListVo);
    }

//    @ApiOperation("下载手续费明细")
//    @PostMapping("/statistics/download")
//    public CommonResponse<List<FeeStatisticsPo>> downloadFeeStatistics(
//            @ApiParam(value = "开始时间", required = true)@RequestParam Date beginTime,
//            @ApiParam(value = "结束时间", required = true)@RequestParam Date endTime
//    ){
//        if(beginTime.getTime() == endTime.getTime()){
//            Calendar c = Calendar.getInstance();
//            c.setTime(endTime);
//            c.add(Calendar.DAY_OF_MONTH, 1);
//            endTime = c.getTime();
//        }
//
//        List<FeeStatisticsPo> feeStatisticsPos = feeService.downloadFeeStatistics(beginTime, endTime);
//
//        return CommonResponse.ok(feeStatisticsPos);
//    }
}
