package com.hengxunda.app.controller;


import com.hengxunda.app.annotation.RoleAuthAnon;
import com.hengxunda.app.dto.AdvertBuyAndSellDto;
import com.hengxunda.app.service.ITransactionService;
import com.hengxunda.app.vo.AdvertPageVo;
import com.hengxunda.app.vo.TransactionPairVo;
import com.hengxunda.common.Enum.OrderTypeEnum;
import com.hengxunda.common.Enum.TransPairLegalEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "交易相关")
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService iTransactionService;

    @ApiOperation("获取交易对(包含行情)")
    @GetMapping("/pair")
    public CommonResponse<List<TransactionPairVo>> getTransactionPairs(){
        return CommonResponse.ok(iTransactionService.getTransactionPairs());
    }


    @ApiOperation("获取广告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "1.买入，0.卖出",required = true,paramType = "path",dataType = "Int"),
            @ApiImplicitParam(name = "page",value = "当前页",required = true,paramType = "path",dataType = "Int"),
            @ApiImplicitParam(name = "rows",value = "每页显示条数",required = true,paramType = "path",dataType = "Int"),
            @ApiImplicitParam(name = "transactionPair",value = "交易对",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/advert/list/{type}/{page}/{rows}")
    public CommonResponse<AdvertPageVo> getAdvertList(
            @PathVariable(name = "type",required = true) Integer type,
            @PathVariable(name = "page",required = true) Integer page,
            @PathVariable(name = "rows",required = true) Integer rows,
            @RequestParam(name = "transactionPair",required = true) String transactionPair
    ){
        A.check(OrderTypeEnum.getEnum(type) == null,"不支持的买卖类型");
        A.check(TransPairLegalEnum.getEnum(transactionPair)==null,"不支持的交易对类型");

        return CommonResponse.ok(iTransactionService.getAdverts(type,transactionPair,page,rows));
    }



    @ApiOperation("买入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "advertBuyAndSellDto",value = "实体类信息",required = true,paramType = "body",dataType = "AdvertBuyAndSellDto")
    })
    @RoleAuthAnon
    @PostMapping("/buy")
    public CommonResponse<String> buy(@RequestBody AdvertBuyAndSellDto advertBuyAndSellDto){

        if (advertBuyAndSellDto.getType() !=0 && advertBuyAndSellDto.getType() !=1)
            return CommonResponse.error("Unsupported type");
        A.check(TransPairLegalEnum.getEnum(advertBuyAndSellDto.getTransactionPair())==null,"Unsupported transactionPair");
        return CommonResponse.ok(iTransactionService.buy1(advertBuyAndSellDto));
    }

    @ApiOperation("卖出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token信息",required = true,paramType = "header",dataType = "string"),
            @ApiImplicitParam(name = "advertBuyAndSellDto",value = "实体类信息",required = true,paramType = "body",dataType = "AdvertBuyAndSellDto")
    })
    @RoleAuthAnon
    @PostMapping("/sell")
    public CommonResponse<String> sell(@RequestBody AdvertBuyAndSellDto advertBuyAndSellDto){

        if (advertBuyAndSellDto.getType() !=0 && advertBuyAndSellDto.getType() !=2)
            return CommonResponse.error("Unsupported type");
        A.check(StringUtils.isBlank(advertBuyAndSellDto.getPayPassword()),"交易密码不能为空");
        A.check(TransPairLegalEnum.getEnum(advertBuyAndSellDto.getTransactionPair())==null,"Unsupported transactionPair");
        return CommonResponse.ok(iTransactionService.sell(advertBuyAndSellDto));
    }

}
