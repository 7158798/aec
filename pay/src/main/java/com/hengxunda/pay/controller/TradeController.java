package com.hengxunda.pay.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.pay.dto.OutTradeDto;
import com.hengxunda.pay.service.OutTradeService;
import com.hengxunda.pay.vo.OutTradeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 加币/扣币
 *
 * @Author: QiuJY
 * @Date: Created in 14:24 2018/3/20
 */

@Api(description = "外部接口-MSC旧接口", tags = "out-api")
@RequestMapping("/outTrade")
@RestController
public class TradeController {

    @Autowired
    private OutTradeService outTradeService;

    @ApiOperation(value = "查询扣币请求是否成功", notes = "返回:success/fail")
    @GetMapping("trade/check")
    public String checkById(@RequestParam(value = "id") String id) {
        return outTradeService.checkById(id);
    }


    /**
     * 1.會員轉帳給商家
     * a. 商家向AEC官網請求訂單號
     * 參數:類別A、用戶名稱、幣種、商家訂單號、金額
     * 2.商家轉帳給會員
     * b. 商家向AEC官網請求訂單號
     * 參數:類別B、用戶名稱、幣種、商家訂單號、金額、接收者之轉帳地址
     *
     * @param outTradeDto
     * @return
     */
    @ApiOperation("生成订单")
    @PostMapping("/trade/generateOrder")
    @ApiImplicitParam(name = "outTradeDto", value = "实体类型", required = true, paramType = "body", dataType = "OutTradeDto")
    public CommonResponse<OutTradeVo> getTrade(@RequestBody OutTradeDto outTradeDto) {
        return CommonResponse.ok(outTradeService.getTrade(outTradeDto));
    }

//    @ApiOperation("商家付款普通用户")
//    @PostMapping("/trade/turnUser")
//    @ApiImplicitParam(name = "outTradeDto", value = "实体类型", required = true, paramType = "body", dataType = "OutTradeDto")
//    public CommonResponse turnUser() {
//        return CommonResponse.ok();
//    }

}
