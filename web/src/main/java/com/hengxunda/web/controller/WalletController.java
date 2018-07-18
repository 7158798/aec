package com.hengxunda.web.controller;

import com.hengxunda.common.Common.WebConstant;
import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.WalletPresetAddress;
import com.hengxunda.web.config.WalletAsync;
import com.hengxunda.web.service.IWalletService;
import com.hengxunda.web.service.impl.WalletPresetAddressServiceImpl;
import com.hengxunda.web.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/13
 */
@Slf4j
@Api(description = "钱包相关")
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private IWalletService iWalletService;
    @Autowired
    private WalletAsync walletAsync;
    @Autowired
    private WalletPresetAddressServiceImpl walletPresetAddressServiceImpl;

    @ApiOperation("钱包明细")
    @PostMapping("/records")
    public CommonResponse<WalletRecordListVo> getWalletRecords(
            @ApiParam(value = "提现币种，非必传（AEC,MSC,BTC,LTC，不传默认为AEC）") @RequestParam(required = false) String coinName,
            @ApiParam(value = "订单号，非必传") @RequestParam(required = false) String orderNo,
            @ApiParam(value = "用户姓名，非必传") @RequestParam(required = false) String name,
            @ApiParam(value = "用户手机，非必传") @RequestParam(required = false) String phone,
            @ApiParam(value = "交易类型0：转出，1：转入，传100获取所有", required = true) @RequestParam int type,
            @ApiParam(value = "备注信息，非必传") @RequestParam(required = false) String tradeRemark,
            @ApiParam(value = "选择日期当天0时0分0秒") @RequestParam(required = false) Date createTime,
            Page page
    ) {
        if (coinName != null && !coinName.equals("")) {
            coinName = coinName.toUpperCase();
        } else {
            coinName = WebConstant.WALLET_RECORDS_DEFALUT;
        }
        WalletRecordListVo walletRecordListVo = iWalletService.getWalletRecords(coinName, orderNo, name, phone, type, tradeRemark, createTime, page);
        return CommonResponse.ok(walletRecordListVo);
    }

    @ApiOperation("提充明细")
    @PostMapping("/recharges")
    public CommonResponse<WalletRechargeListVo> getRecharges(
            @ApiParam(value = "提现币种，非必传（AEC,MSC,BTC,LTC，不传默认为AEC）") @RequestParam(required = false) String coinName,
            @ApiParam(value = "用户姓名，非必传") @RequestParam(required = false) String name,
            @ApiParam(value = "用户手机，非必传") @RequestParam(required = false) String phone,
            @ApiParam(value = "交易类型0：充币，1：提币，传100获取所有", required = true) @RequestParam int type,
            @ApiParam(value = "交易类型0：收币成功，1：提币成功，传100获取所有", required = true) @RequestParam int status,
            @ApiParam(value = "备注信息，非必传") @RequestParam(required = false) String remark,
            @ApiParam(value = "选择日期当天0时0分0秒") @RequestParam(required = false) Date createTime,
            Page page
    ) {
        if (coinName != null && !coinName.equals("")) {
            coinName = coinName.toUpperCase();
        } else {
            coinName = WebConstant.WALLET_RECORDS_DEFALUT;
        }
        WalletRechargeListVo walletRechargeListVo = iWalletService.getRechargeLogs(coinName, name, phone, type, status, remark, createTime, page);
        return CommonResponse.ok(walletRechargeListVo);
    }

    @ApiOperation("兑换明细")
    @PostMapping("/exchanges")
    public CommonResponse<WalletExchangeListVo> getExchanges(
            @ApiParam(value = "提现币种，非必传（AEC,MSC,BTC,LTC，不传默认为AEC）") @RequestParam(required = false) String coinName,
            @ApiParam(value = "用户姓名，非必传") @RequestParam(required = false) String name,
            @ApiParam(value = "用户手机，非必传") @RequestParam(required = false) String phone,
            @ApiParam(value = "兑换类型：xx转xx") @RequestParam(required = false) String type,
            @ApiParam(value = "选择日期当天0时0分0秒") @RequestParam(required = false) Date createTime,
            Page page
    ) {
        WalletExchangeListVo walletExchangeListVo = iWalletService.getExchangeLogs(name, phone, type, createTime, page);
        return CommonResponse.ok(walletExchangeListVo);
    }

    @ApiOperation("拨币明细")
    @PostMapping("/dials")
    public CommonResponse<WalletDialCoinListVo> getDialCoins(
            @ApiParam(value = "提现币种，非必传（AEC,MSC,BTC,LTC，不传默认为AEC）") @RequestParam(required = false) String coinName,
            @ApiParam(value = "用户姓名，非必传") @RequestParam(required = false) String name,
            @ApiParam(value = "用户手机，非必传") @RequestParam(required = false) String phone,
            @ApiParam(value = "类型，0.平台拨币,10.差额奖励,12.股权分红，13.代数奖励,100返回所有",required = true) @RequestParam int type,
            @ApiParam(value = "0：未锁仓，1：锁仓,100返回所有", required = true) @RequestParam int status,
            @ApiParam(value = "选择日期当天0时0分0秒") @RequestParam(required = false) Date createTime,
            Page page
    ) {

        WalletDialCoinListVo walletDialCoinListVo = iWalletService.getDialCoinLogs(coinName, name, phone, type, status, createTime, page);
        return CommonResponse.ok(walletDialCoinListVo);
    }

    @ApiOperation("系统钱包统计")
    @PostMapping("/total")
    public CommonResponse<WalletTotalInfoListVo> getTotal(
            Page page
    ) {

        WalletTotalInfoListVo walletTotalInfoListVo = iWalletService.getWalletInfoByCoin(page);
        return CommonResponse.ok(walletTotalInfoListVo);
    }
/*   */

    /**
     * @Author:zhangwenjun:
     * @Description:
     * @Param :
     * @Date 2018/6/20 12:25
     *//*
    @ApiOperation("预置比特币钱包地址")
    @PostMapping("/presetBTCAddress")
    public CommonResponse presetBTCAddress(@ApiParam(value = "生成BTC地址条数", required = true) @RequestParam Long num) {
        if (num > 0) {
            Long oldNum= walletAsync.getWalletAddressMaxAccountId(WalletTypeEnum.BTC.getCode());
            for (long i = 1; i <= num; i++) {
                walletAsync.BTCWalletAddress(i+oldNum);
            }
        }
        return CommonResponse.ok("预置BTC地址成功！");
    }*/
    @ApiOperation("预置比特币钱包地址")
    @PostMapping("/presetBTCAddressBatch")
    public CommonResponse presetBTCAddressBatch(@ApiParam(value = "生成BTC地址条数", required = true) @RequestParam Long num) {

        walletAsync.BTCWalletAddressAsync(num);
        return CommonResponse.ok("预置BTC地址成功！");
    }

    @ApiOperation("预置莱特币钱包地址")
    @PostMapping("/presetLTCAddress")
    public CommonResponse presetLTCAddress(@ApiParam(value = "生成LTC地址条数", required = true) @RequestParam Long num) {
        walletAsync.LTCWalletAddressAsync(num);
        return CommonResponse.ok("预置LTC地址成功!");
    }

    @ApiOperation("预置ETH钱包地址")
    @PostMapping("/presetETHAddress")
    public CommonResponse presetETHAddress(
            @ApiParam(value = "生成ETH地址条数", required = true) @RequestParam Long num,
            @ApiParam(value = "密码", required = false) @RequestParam(required = false) String password) {
        walletAsync.ETHWalletAddressAsync(num, password);
        return CommonResponse.ok("预置ETH地址成功!");
    }


    @ApiOperation("地址使用统计")
    @GetMapping("/getStatisticsAddress")
    public CommonResponse getStatisticsAddress() {
        List<AddressStatisticsVo> addressStatisticsVoList=  walletPresetAddressServiceImpl.getStatisticsAddress();
        return CommonResponse.ok(addressStatisticsVoList);
    }


}
