package com.hengxunda.wapp.controller;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.po.AdvertListPo;
import com.hengxunda.wapp.dto.AdvertDto;
import com.hengxunda.wapp.service.IAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/advert")
@Api(value = "/advert", description = "广告管理")
public class AdvertController {

    @Autowired
    IAdvertService advertService;

    @ApiOperation(value = "发布广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertDto", value = "发布广告请求对象", required = true, paramType = "body", dataType = "AdvertDto"),
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    })
    @PostMapping("publish")
    public CommonResponse publish(@RequestBody AdvertDto advertDto) {
        check(advertDto);
        advertService.save(advertDto);
        return CommonResponse.ok();
    }

    @ApiOperation(value = "编辑广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertDto", value = "编辑广告请求对象", required = true, paramType = "body", dataType = "AdvertDto"),
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    })
    @PutMapping("edit")
    public CommonResponse edit(@RequestBody AdvertDto advertDto) {
        A.check(StringUtils.isBlank(advertDto.getId()), "广告id不能为空");
        check(advertDto);
        advertService.save(advertDto);
        return CommonResponse.ok();
    }

    private void check(AdvertDto advertDto) {
        A.check(Objects.isNull(advertDto.getType()), "交易类型不能为空");
        A.check(Objects.isNull(advertDto.getUnitPrice()), "单价不能为空");
        A.check(Objects.isNull(advertDto.getStatus()), "广告状态不能为空");
    }

    @ApiOperation(value = "获取广告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "交易类型(0.卖出，1.买入)", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "transactionPair", value = "交易对(格式如：AEC/CNY)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "loginStatus", value = "登录状态(0.在线，1.离线)", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "广告状态(0.上架，1.下架)", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "银商id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "当前页(默认第1页)", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条记录数(默认每页15条)", paramType = "query", dataType = "Long")
    })
    @GetMapping("list")
    public CommonResponse<List<AdvertListPo>> list(@RequestParam(value = "type", defaultValue = "0") Integer type,
                                                   @RequestParam(value = "transactionPair", defaultValue = "AEC/CNY") String transactionPair,
                                                   @RequestParam(value = "loginStatus", defaultValue = "0") Integer loginStatus,
                                                   @RequestParam(value = "status", required = false) Integer status,
                                                   @RequestParam(value = "userId", required = false) String userId,
                                                   @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize) {

        return CommonResponse.ok(advertService.findList(type, transactionPair, loginStatus, userId, status, pageNo, pageSize));
    }

    @ApiOperation(value = "获取发布广告单价区间限额")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("interval")
    public CommonResponse interval() {

        return CommonResponse.ok(
                new StringBuilder()
                        .append(advertService.getGlobalParameterByKey(GlobalParameterEnum.AdvertMinAec.getCode()))
                        .append("-")
                        .append(advertService.getGlobalParameterByKey(GlobalParameterEnum.AdvertMaxAec.getCode())));
    }

    @ApiOperation(value = "广告下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "广告id", required = true, paramType = "query", dataType = "String")
    })
    @PutMapping("soldOut")
    public CommonResponse soldOut(@RequestParam("id") String id) {
        A.check(StringUtils.isBlank(id), "广告id不能为空");
        advertService.soldOut(id);
        return CommonResponse.ok();
    }
}

