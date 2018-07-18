package com.hengxunda.app.controller;

import com.hengxunda.app.dto.AKeyTurnAecDto;
import com.hengxunda.app.dto.AKeyTurnMscDto;
import com.hengxunda.app.dto.TradeDto;
import com.hengxunda.app.service.IWalletService;
import com.hengxunda.app.vo.CoinRateVo;
import com.hengxunda.app.vo.OrderInfoVo;
import com.hengxunda.app.vo.WalletInfoVo;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.dao.po.app.WalletRecordPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(description = "普通用户钱包管理")
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private IWalletService iWalletService;

    @ApiOperation("获取币币交易汇率")
    @GetMapping("/coinrate")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    public CommonResponse<List<CoinRateVo>> getTransactionPairs() {
        return CommonResponse.ok(iWalletService.coinRate());
    }


    @ApiOperation("钱包记录")
    @GetMapping("/walletrecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "当前页(默认第1页)", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条记录数(默认每页15条)", paramType = "query", dataType = "Long")
    })
    public CommonResponse<List<WalletRecordPo>> getWalletRecord(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                                @RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize) {

        return CommonResponse.ok(iWalletService.getWalletRecordByUserId(pageNo, pageSize));
    }


    @ApiOperation("获取某个用户的钱包信息")
    @GetMapping("/walletinfo")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    public CommonResponse<WalletInfoVo> getWalletInfo() {
        WalletInfoVo walletInfoVo = iWalletService.getWalletInfoByUserId();
        return CommonResponse.ok(walletInfoVo);
    }

    @ApiOperation("提币")
    @PostMapping("/bringcoin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tradeDto", value = "交易实体类", required = true, paramType = "body", dataType = "TradeDto")
    })
    public CommonResponse bringCoin(@RequestBody TradeDto tradeDto) {
        A.check(StringUtils.isBlank(tradeDto.getToAddress()), "目标钱包地址不能为空!");
        A.check(StringUtils.isBlank(tradeDto.getFromAddress()), "源钱包地址不能为空!");
        iWalletService.bringCoin(tradeDto);
        return CommonResponse.ok();
    }

    @ApiOperation("转账、付款操作")
    @PostMapping("/transfer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "tradeDto", value = "交易实体类", required = true, paramType = "body", dataType = "TradeDto")
    })
    public CommonResponse transfer(@RequestBody TradeDto tradeDto) {
        A.check(StringUtils.isBlank(tradeDto.getToAddress()), "目标钱包地址不能为空!");
        A.check(StringUtils.isBlank(tradeDto.getFromAddress()), "源钱包地址不能为空!");
        iWalletService.transfer(tradeDto);
        return CommonResponse.ok();
    }


    @ApiOperation("查询商家订单信息")
    @GetMapping("/orderInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, paramType = "query", dataType = "String")
    })
    public CommonResponse<OrderInfoVo> orderInfo(@RequestParam("orderId") String orderId){
        return CommonResponse.ok(iWalletService.orderInfo(orderId));
    }

    @ApiOperation("一键转币（其他币转AEC币）")
    @PostMapping("/akeyturnAec")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "aKeyTurnAecDto", value = "一键转币（其他币转AEC币）实体类", required = true, paramType = "body", dataType = "AKeyTurnAecDto")
    })
    public CommonResponse aKeyTurnAec(@RequestBody AKeyTurnAecDto aKeyTurnAecDto) {
        A.check(MathUtils.equalForBg(aKeyTurnAecDto.getBtcAmount(), BigDecimal.ZERO)&&MathUtils.equalForBg(aKeyTurnAecDto.getLtcAmount(), BigDecimal.ZERO), "BTC、LTC数量不能同时为0");
        iWalletService.aKeyTurnAec(aKeyTurnAecDto);
        return CommonResponse.ok();
    }


    @ApiOperation("一键转币（AEC币转MSC币）")
    @PostMapping("/aectrunmsc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "aKeyTurnMscDto", value = "一键转币（AEC币转MSC币）实体类", required = true, paramType = "body", dataType = "AKeyTurnMscDto")
    })
    public CommonResponse aecTrunMsc(@RequestBody AKeyTurnMscDto aKeyTurnMscDto) {
        iWalletService.aecTrunMsc(aKeyTurnMscDto);
        return CommonResponse.ok();
    }
}
