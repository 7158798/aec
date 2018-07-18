package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.Page;
import com.hengxunda.web.service.IShopSercive;
import com.hengxunda.web.vo.ShopInfoListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lsl
 * @Date: create in 2018/6/19
 */
@Slf4j
@Api(description = "商户管理")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private IShopSercive shopSercive;

    @ApiOperation("获取商户列表")
    @GetMapping("/shops")
    public CommonResponse<ShopInfoListVo> getShopInfos(
            @ApiParam(value = "商户id，传入则只查询该用户，非必传")@RequestParam(required = false) String id,
            @ApiParam(value = "商户手机号，传入则只查询该用户，非必传")@RequestParam(required = false) String mobile,
            @ApiParam(value = "商户姓名，非必传，不传则返回所有")@RequestParam(required = false) String name,
            @ApiParam(value = "商户昵称，非必传，不传则返回所有")@RequestParam(required = false) String nickName,
            @ApiParam(value = "用户等级，100.返回所有类型", required = true)@RequestParam int level,
            Page page
    ){

        ShopInfoListVo shopInfoListVo = shopSercive.getShopInfos(id,name,nickName, mobile, level, page);

        return CommonResponse.ok(shopInfoListVo);
    }
}
