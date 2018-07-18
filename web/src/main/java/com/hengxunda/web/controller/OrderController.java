package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.OrderAppealPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import com.hengxunda.web.service.IOrderService;
import com.hengxunda.web.vo.OrderAppealListVo;
import com.hengxunda.web.vo.OrderListVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/11
 */
@Slf4j
@Api(description = "订单管理")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @ApiOperation("获取订单列表")
    @GetMapping("/orders")
    public CommonResponse<OrderListVo> getOrders(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String orderNo,
            @ApiParam(value = "用户手机，非必传")@RequestParam(required = false) String buyPhone,
            @ApiParam(value = "用户姓名，非必传")@RequestParam(required = false) String buyName,
            @ApiParam(value = "银商手机，非必传")@RequestParam(required = false) String sellPhone,
            @ApiParam(value = "银商姓名，非必传")@RequestParam(required = false) String sellName,
            @ApiParam(value = "数量，非必传")@RequestParam(required = false) BigDecimal number,
            @ApiParam(value = "单价，非必传")@RequestParam(required = false) BigDecimal price,
            @ApiParam(value = "总额，非必传")@RequestParam(required = false) BigDecimal amount,
            @ApiParam(value = "下单时间，非必传")@RequestParam(required = false) Date createTime,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            @ApiParam(value = "订单状态，0.未支付，1，已付款，2.申诉中，3.已取消，4.完成，传100则返回所有", required = true)@RequestParam int status,
            Page page
    ){

        OrderListVo orderListVo = iOrderService.getOrdersForWeb(orderNo, buyPhone, buyName, sellPhone, sellName, number, price, amount, type, status, createTime, page);
        return CommonResponse.ok(orderListVo);
    }

    @ApiOperation("仲裁申诉订单")
    @PostMapping("/reviewOrderAppeal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appealId", value = "申诉订单ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "申诉结果", required = true, dataType = "Long", paramType = "query")
    })
    public CommonResponse reviewAppealOrder(@RequestParam("appealId") String appealId,@RequestParam("status") Integer status) {
        iOrderService.reviewAppealOrder(appealId,status);
        return CommonResponse.ok();
    }

    @ApiOperation("下载选取时间内的订单")
    @PostMapping("/orders_download")
    public CommonResponse<List<OrderWebPo>> ordersDownload(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String orderNo,
            @ApiParam(value = "用户手机，非必传")@RequestParam(required = false) String buyPhone,
            @ApiParam(value = "用户姓名，非必传")@RequestParam(required = false) String buyName,
            @ApiParam(value = "银商手机，非必传")@RequestParam(required = false) String sellPhone,
            @ApiParam(value = "银商姓名，非必传")@RequestParam(required = false) String sellName,
            @ApiParam(value = "数量，非必传")@RequestParam(required = false) BigDecimal number,
            @ApiParam(value = "单价，非必传")@RequestParam(required = false) BigDecimal price,
            @ApiParam(value = "总额，非必传")@RequestParam(required = false) BigDecimal amount,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            @ApiParam(value = "订单状态，0.未支付，1，已付款，2.申诉中，3.已取消，4.完成，传100则返回所有", required = true)@RequestParam int status,
            @ApiParam(value = "下单时间，非必传", required = true)@RequestParam Date beginTime,
            @ApiParam(value = "下单时间，非必传", required = true)@RequestParam Date endTime,
            Page page
    ){

        List<OrderWebPo> orderWebPos = iOrderService.downloadOrdersForWeb(orderNo, buyPhone, buyName, sellPhone, sellName, number, price, amount, type, status,beginTime,endTime);
        return CommonResponse.ok(orderWebPos);
    }

    @ApiOperation("获取订单申诉列表")
    @GetMapping("/appeals")
    public CommonResponse<OrderAppealListVo> getOrderAppeals(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String orderNo,
            @ApiParam(value = "用户手机，非必传")@RequestParam(required = false) String buyPhone,
            @ApiParam(value = "用户姓名，非必传")@RequestParam(required = false) String buyName,
            @ApiParam(value = "银商手机，非必传")@RequestParam(required = false) String sellPhone,
            @ApiParam(value = "银商姓名，非必传")@RequestParam(required = false) String sellName,
            @ApiParam(value = "用户角色：0.普通用户，1.商家，2.银商，传100则返回所有", required = true)@RequestParam int role,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            @ApiParam(value = "订单状态，0.未支付，1，已付款，2.申诉中，3.已取消，4.完成，传100则返回所有", required = true)@RequestParam int status,
            Page page
    ){

        OrderAppealListVo orderAppealListVo = iOrderService.getOrderAppeals(orderNo, buyPhone, buyName, sellPhone, sellName, role, type, status, page);
        return CommonResponse.ok(orderAppealListVo);
    }

    @ApiOperation("下载选取时间内的申诉")
    @PostMapping("/appeal_download")
    public CommonResponse<List<OrderAppealPo>> orderAppealsDownload(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String orderNo,
            @ApiParam(value = "用户手机，非必传")@RequestParam(required = false) String buyPhone,
            @ApiParam(value = "用户姓名，非必传")@RequestParam(required = false) String buyName,
            @ApiParam(value = "银商手机，非必传")@RequestParam(required = false) String sellPhone,
            @ApiParam(value = "银商姓名，非必传")@RequestParam(required = false) String sellName,
            @ApiParam(value = "用户角色：0.普通用户，1.商家，2.银商，传100则返回所有", required = true)@RequestParam int role,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            @ApiParam(value = "订单状态，0.未支付，1，已付款，2.申诉中，3.已取消，4.完成，传100则返回所有", required = true)@RequestParam int status,
            @ApiParam(value = "下单时间，非必传", required = true)@RequestParam Date beginTime,
            @ApiParam(value = "下单时间，非必传", required = true)@RequestParam Date endTime,
            Page page
    ){

        List<OrderAppealPo> orderAppealPos = iOrderService.downloadOrderAppeals(orderNo, buyPhone, buyName, sellPhone, sellName, role, type, status,beginTime,endTime);
        return CommonResponse.ok(orderAppealPos);
    }
}
