package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.AdvertWebPo;
import com.hengxunda.dao.po.web.BondApplyPo;
import com.hengxunda.dao.po.web.MerchantApplyPo;
import com.hengxunda.web.service.IMerchantService;
import com.hengxunda.web.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Slf4j
@Api(description = "银商管理")
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    private IMerchantService iMerchantService;

    @ApiOperation("获取银商列表")
    @GetMapping("/getMerchants")
    public CommonResponse<MerchantListVo> getMerchants(
            @ApiParam(value = "用户userId，传入则只查询该用户，非必传") @RequestParam(required = false) String userId,
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "用户昵称，非必传，不传则返回所有") @RequestParam(required = false) String nickName,
            @ApiParam(value = "用户注册时间，传入型如yyyy-MM-dd 00:00:00字符串即可，非必传") @RequestParam(required = false) String creatTime,
            Page page
    ) {

        MerchantListVo merchantListVo = iMerchantService.getMerchants(userId, mobile, name, nickName, creatTime, page);
        return CommonResponse.ok(merchantListVo);
    }

    @ApiOperation("下载选取时间范围内的银商数据")
    @PostMapping("/merchants_download")
    public CommonResponse<List<MerchantVo>> downloadMerchants(
            @ApiParam(value = "用户userId，传入则只查询该用户，非必传") @RequestParam(required = false) String userId,
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "用户昵称，非必传，不传则返回所有") @RequestParam(required = false) String nickName,
            @ApiParam(value = "开始时间", required = true) @RequestParam Date beginTime,
            @ApiParam(value = "开始时间", required = true) @RequestParam Date endTime,
            HttpServletResponse response
    ) {

        if (beginTime.getTime() == endTime.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endTime = c.getTime();
        }
        List<MerchantVo> list = iMerchantService.downLoadExcel(userId, mobile, name, nickName, beginTime, endTime, response);
        return CommonResponse.ok(list);
    }

    @ApiOperation("获取指定银商的详细信息")
    @PostMapping("/merchant_id")
    public CommonResponse<MerchantDetailVo> getMerchantById(
            @ApiParam(value = "银商id", required = true) @RequestParam String id
    ) {
        MerchantDetailVo merchantDetailVo = iMerchantService.getMerchantDetailById(id);

        return CommonResponse.ok(merchantDetailVo);
    }


    @ApiOperation("获取银商申请列表")
    @GetMapping("/apply/getMerchants")
    public CommonResponse<MerchantApplyVo> getMerchants(
            @ApiParam(value = "用户UID，传入则只查询该用户，非必传") @RequestParam(required = false) String uid,
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "状态，0.申请中，1.驳回，2.通过。传100返回(0,1,2)状态，传3返回意向申请者", required = true) @RequestParam int status,
            Page page
    ) {

        MerchantApplyVo merchantApplys = iMerchantService.getMerchantApplys(uid, mobile, name, status, page);
        return CommonResponse.ok(merchantApplys);
    }

    @ApiOperation("获取指定银商申请者的详细信息")
    @PostMapping("/apply/merchant_id")
    public CommonResponse<MerchantDetailVo> getMerchantApplyById(
            @ApiParam(value = "银商id", required = true) @RequestParam String id
    ) {
        MerchantDetailVo merchantDetailVo = iMerchantService.getMerchantApplyInfoById(id);

        return CommonResponse.ok(merchantDetailVo);
    }

    @ApiOperation("通过/不通过银商申请")
    @PostMapping("/apply/review")
    public CommonResponse reviewMerchantApply(
            @ApiParam(value = "银商id", required = true) @RequestParam String id,
            @ApiParam(value = "申请审核结果1.驳回，2.通过", required = true) @RequestParam int status
    ) {

        try {
            int result = iMerchantService.reviewMerchantApply(id, status);
            if (result == 1) {
                return CommonResponse.ok("审核完成");
            }
        } catch (Exception e) {
            return CommonResponse.error(e.getMessage());
        }
        return null;

    }

    @ApiOperation("下载选取时间范围内的银商申请数据")
    @PostMapping("/apply/merchants_download")
    public CommonResponse<List<MerchantApplyPo>> downloadApplyMerchants(
            @ApiParam(value = "用户UID，传入则只查询该用户，非必传") @RequestParam(required = false) String uid,
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "状态，0.申请中，1.驳回，2.通过。传100返回(0,1,2)状态，传3返回意向申请者", required = true) @RequestParam int status,
            @ApiParam(value = "开始时间", required = true) @RequestParam Date beginTime,
            @ApiParam(value = "开始时间", required = true) @RequestParam Date endTime
    ) {

        if (beginTime.getTime() == endTime.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endTime = c.getTime();
        }
        List<MerchantApplyPo> list = iMerchantService.downlodApllys(uid, mobile, name, status, beginTime, endTime);
        return CommonResponse.ok(list);
    }

    @ApiOperation("获取广告列表")
    @GetMapping("/adverts")
    public CommonResponse<AdvertListVo> getAdverts(
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "保证金，非必传") @RequestParam(required = false) BigDecimal bond,
            @ApiParam(value = "广告类型，0.卖出，1.买入，传100则返回所有", required = true) @RequestParam int type,
            @ApiParam(value = "广告状态，0.上架，1.下架，传100则返回所有", required = true) @RequestParam int status,
            @ApiParam(value = "最低额，非必传") @RequestParam(required = false) BigDecimal minValue,
            @ApiParam(value = "单价，非必传") @RequestParam(required = false) BigDecimal price,
            @ApiParam(value = "创建时间，非必传") @RequestParam(required = false) Date createTime,
            Page page
    ) {

        AdvertListVo advertListVo = iMerchantService.getAdvertWebs(mobile, name, bond, type, status, minValue, price, createTime, page);
        return CommonResponse.ok(advertListVo);
    }

    @ApiOperation("下载广告信息")
    @PostMapping("/adverts_download")
    public CommonResponse<List<AdvertWebPo>> downloadAdverts(
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "保证金，非必传") @RequestParam(required = false) BigDecimal bond,
            @ApiParam(value = "广告类型，0.卖出，1.买入，传100则返回所有", required = true) @RequestParam int type,
            @ApiParam(value = "广告状态，0.上架，1.下架，传100则返回所有", required = true) @RequestParam int status,
            @ApiParam(value = "最低额，非必传") @RequestParam(required = false) BigDecimal minValue,
            @ApiParam(value = "单价，非必传") @RequestParam(required = false) BigDecimal price,
            @ApiParam(value = "开始时间", required = true) @RequestParam Date beginTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam Date endTime
    ) {

        if (beginTime.getTime() == endTime.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endTime = c.getTime();
        }
        List<AdvertWebPo> advertWebPos = iMerchantService.downloadAdverts(mobile, name, bond, type, status, minValue, price, beginTime, endTime);
        return CommonResponse.ok(advertWebPos);
    }

    @ApiOperation("获取保证金申请列表")
    @GetMapping("/bonds")
    public CommonResponse<BondApplyListVo> getBonds(
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "申请类型，0.申请提高，1.申请降低，传100则返回所有", required = true) @RequestParam int type,
            @ApiParam(value = "申请状态，0.申请中，1.审核通过，2.审核不通过，传100则返回所有", required = true) @RequestParam int status,
            @ApiParam(value = "申请提高/降低保证金数量，非必传") @RequestParam(required = false) BigDecimal change,
            @ApiParam(value = "当前保证金，非必传") @RequestParam(required = false) BigDecimal original,
            Page page
    ) {

        BondApplyListVo bondApplyListVo = iMerchantService.getBondApplys(mobile, name, type, status, change, original, page);
        return CommonResponse.ok(bondApplyListVo);
    }

    @ApiOperation("下载保证金申请列表")
    @PostMapping("/bonds/download")
    public CommonResponse<List<BondApplyPo>> bondsDownload(
            @ApiParam(value = "用户手机号，传入则只查询该用户，非必传") @RequestParam(required = false) String mobile,
            @ApiParam(value = "用户姓名，非必传，不传则返回所有") @RequestParam(required = false) String name,
            @ApiParam(value = "申请类型，0.申请提高，1.申请降低，传100则返回所有", required = true) @RequestParam int type,
            @ApiParam(value = "申请状态，0.申请中，1.审核通过，2.审核不通过，传100则返回所有", required = true) @RequestParam int status,
            @ApiParam(value = "申请提高/降低保证金数量，非必传") @RequestParam(required = false) BigDecimal change,
            @ApiParam(value = "当前保证金，非必传") @RequestParam(required = false) BigDecimal original,
            @ApiParam(value = "开始时间", required = true) @RequestParam Date beginTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam Date endTime
    ) {

        if (beginTime.getTime() == endTime.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endTime = c.getTime();
        }

        List<BondApplyPo> bondApplyPos = iMerchantService.downloadBondApplys(mobile, name, type, status, change, original, beginTime, endTime);
        return CommonResponse.ok(bondApplyPos);
    }

    @ApiOperation("通过/不通过保证金申请")
    @PostMapping("/bond/review")
    public CommonResponse reviewBondApply(
            @ApiParam(value = "id", required = true) @RequestParam String id,
            @ApiParam(value = "银商id", required = true) @RequestParam String userId,
            @ApiParam(value = "申请审核结果(1.审核通过，2.审核不通过)", required = true) @RequestParam Integer status,
            @ApiParam(value = "申请类型(1.降低保证金，2.提取保证金)", required = true) @RequestParam Integer type,
            @ApiParam(value = "申请提高/降低保证金数量(提高无需审核，降低需要审核)", required = true) @RequestParam BigDecimal amount,
            @ApiParam(value = "原因描述", required = true) @RequestParam String reason
    ) {
        iMerchantService.reviewBondApply(id, userId, status, type, amount, reason);
        return CommonResponse.ok();

    }
}
