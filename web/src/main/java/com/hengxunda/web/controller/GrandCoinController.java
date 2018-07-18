package com.hengxunda.web.controller;

import com.google.gson.reflect.TypeToken;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.web.oauth2.ShiroUtils;
import com.hengxunda.web.service.GrandCoinService;
import com.hengxunda.web.vo.GrantCoinTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 平台拨币
 * @Author: QiuJY
 * @Date: Created in 10:43 2018/6/15
 */
@Slf4j
@Api(description = "拨币管理", tags = "operation-grant")
@RestController
@RequestMapping("operation/grant")
public class GrandCoinController {

    @Autowired
    private GrandCoinService grandCoinService;

    @ApiOperation("校验拨币信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "[{\"uid\":\"15\",\"aecAmont\":20,\"mscAmount\":-20,\"isLock\":\"0\"},{\"uid\":\"15\",\"aecAmont\":20,\"mscAmount\":-20,\"isLock\":\"1\"}]", name = "list", paramType = "body")
    })
    @PostMapping("check")
    public CommonResponse<Map<String,Object>> check(@RequestBody  List<GrantCoinTypeVo> list ) {
        return CommonResponse.ok(grandCoinService.check(list));
    }

    @ApiOperation("拨币")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "[{\"uid\":\"15\",\"aecAmont\":20,\"mscAmount\":-20,\"isLock\":\"0\"},{\"uid\":\"15\",\"aecAmont\":20,\"mscAmount\":-20,\"isLock\":\"1\"}]", name = "list", paramType = "body")
    })
    @PutMapping("grantCoin")
    public CommonResponse<List<GrantCoinTypeVo>> grantCoin(@RequestBody  List<GrantCoinTypeVo> list  ) {
        grandCoinService.grantCoin(list, ShiroUtils.getShiroUser().getId());
        return CommonResponse.ok();
    }
}
