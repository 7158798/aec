package com.hengxunda.pay.controller;

import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.pay.dto.BalanceDto;
import com.hengxunda.pay.dto.BringCoinDto;
import com.hengxunda.pay.dto.UserPayDto;
import com.hengxunda.pay.service.IWalletService;
import com.hengxunda.pay.vo.BalanceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "商家API管理")
public class PayController {

    @Autowired
    IWalletService walletService;

    @ApiOperation("普通用户付款")
    @ApiImplicitParam(name = "userPayDto", value = "普通用户付款请求对象", required = true, paramType = "body", dataType = "UserPayDto")
    @PostMapping("pay")
    public CommonResponse pay(@RequestBody UserPayDto userPayDto) {

        A.loopCheck(userPayDto);

        walletService.pay(userPayDto);
        return CommonResponse.ok();
    }

    @ApiOperation("查询用户余额")
    @ApiImplicitParam(name = "balanceDto", value = "查询用户余额请求对象", required = true, paramType = "body", dataType = "BalanceDto")
    @PostMapping("balance")
    public CommonResponse<BalanceVo> balance(@RequestBody BalanceDto balanceDto){
        return CommonResponse.ok(walletService.balance(balanceDto));
    }

    @ApiOperation("提现接口/批量提币")
    @ApiImplicitParam(name = "bringCoinDto", value = "提现请求对象", required = true, paramType = "body", dataType = "BringCoinDto")
    @PutMapping("bringCoin")
    public CommonResponse bringCoin(@RequestBody BringCoinDto bringCoinDto){
        walletService.bringCoin(bringCoinDto);
        return CommonResponse.ok();
    }

}
