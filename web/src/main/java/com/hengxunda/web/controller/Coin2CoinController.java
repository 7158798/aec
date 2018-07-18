package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.Coin2CoinAdvertPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import com.hengxunda.web.service.Coin2CoinService;
import com.hengxunda.web.vo.Coin2CoinAdvertListVo;
import com.hengxunda.web.vo.OrderListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * 币币订单
 * @Author: QiuJY
 * @Date: Created in 14:21 2018/7/9
 */
@Slf4j
@Api(description = "订单管理")
@RestController
@RequestMapping("/coin2coin")
public class Coin2CoinController {

    @Autowired
    private Coin2CoinService coin2CoinService;

    @ApiOperation("获取订单列表")
    @GetMapping("/orders")
    public CommonResponse<OrderListVo> getOrders(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String orderNo,
            @ApiParam(value = "买家手机，非必传")@RequestParam(required = false) String buyPhone,
            @ApiParam(value = "买家昵称，非必传")@RequestParam(required = false) String buyName,
            @ApiParam(value = "卖家手机，非必传")@RequestParam(required = false) String sellPhone,
            @ApiParam(value = "卖家昵称，非必传")@RequestParam(required = false) String sellName,
            @ApiParam(value = "下单时间，非必传")@RequestParam(required = false) Date createTime,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            Page page
    ){

        OrderListVo orderListVo = coin2CoinService.getCoin2CoinOrders(orderNo, buyPhone, buyName, sellPhone, sellName, type, createTime, page);
        return CommonResponse.ok(orderListVo);
    }

    @ApiOperation("下载选取时间内订单")
    @GetMapping("/downloadOrders")
    public CommonResponse<List<OrderWebPo>> downloadOrders(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String orderNo,
            @ApiParam(value = "买家手机，非必传")@RequestParam(required = false) String buyPhone,
            @ApiParam(value = "买家昵称，非必传")@RequestParam(required = false) String buyName,
            @ApiParam(value = "卖家手机，非必传")@RequestParam(required = false) String sellPhone,
            @ApiParam(value = "卖家昵称，非必传")@RequestParam(required = false) String sellName,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            @ApiParam(value = "下单开始时间")@RequestParam(required = true) Date beginTime,
            @ApiParam(value = "下单结束时间")@RequestParam(required = true) Date endTime
    ){

        List<OrderWebPo> orderList = coin2CoinService.downloadOrders(orderNo, buyPhone, buyName, sellPhone, sellName, type, beginTime, endTime);
        return CommonResponse.ok(orderList);
    }


    @ApiOperation("获取币币广告列表")
    @GetMapping("/advert")
    public CommonResponse<Coin2CoinAdvertListVo> getAdvert(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String id,
            @ApiParam(value = "手机，非必传")@RequestParam(required = false) String phone,
            @ApiParam(value = "昵称，非必传")@RequestParam(required = false) String nick,
            @ApiParam(value = "下单时间，非必传")@RequestParam(required = false) Date createTime,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            Page page
    ){

        Coin2CoinAdvertListVo coin2CoinAdvertLisVo = coin2CoinService.getAdvert(id, phone, nick, type, createTime, page);
        return CommonResponse.ok(coin2CoinAdvertLisVo);
    }

    @ApiOperation("下载选取时间内广告列表")
    @GetMapping("/downloadAdvert")
    public CommonResponse<List<Coin2CoinAdvertPo>> downloadAdvert(
            @ApiParam(value = "订单号，非必传")@RequestParam(required = false) String id,
            @ApiParam(value = "手机，非必传")@RequestParam(required = false) String phone,
            @ApiParam(value = "昵称，非必传")@RequestParam(required = false) String nick,
            @ApiParam(value = "订单类型，0.卖出,1.买入，传100则返回所有", required = true)@RequestParam int type,
            @ApiParam(value = "下单开始时间")@RequestParam(required = true) Date beginTime,
            @ApiParam(value = "下单结束时间")@RequestParam(required = true) Date endTime
    ){

        List<Coin2CoinAdvertPo> advertList  = coin2CoinService.downloadAdvert(id, phone, nick, type, beginTime, endTime);
        return CommonResponse.ok(advertList);
    }
}
