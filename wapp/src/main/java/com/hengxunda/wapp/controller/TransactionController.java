package com.hengxunda.wapp.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.wapp.service.ITransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Api(value = "/transaction", description = "交易相关管理")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("transactionPair")
    @ApiOperation(value = "获取交易对")
    public CommonResponse<List<String>> transactionPair() {

        return CommonResponse.ok(transactionService.getTransactionPair());
    }


}


