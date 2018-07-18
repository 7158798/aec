package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.entity.GenerationAwardParameter;
import com.hengxunda.dao.entity.LevelAwardParameter;
import com.hengxunda.web.oauth2.ShiroUtils;
import com.hengxunda.web.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Slf4j
@Api(description = "运营管理", tags = "operation-settings")
@RestController
@RequestMapping("operation/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @ApiOperation(value = "修改参数设置", notes = "返回:无")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "配置信息[{\"AEC/CNY\":\"1.5\", \"msc_increase_split\": \"3000\"}]", name = "map", paramType = "body")
    })
    @PutMapping("")
    public CommonResponse edit(@RequestBody @ApiIgnore Map<String, String> map) {
        this.settingService.update(map,ShiroUtils.getShiroUser());
        return CommonResponse.ok();
    }

    @ApiOperation(value = "获取参数设置", notes = "参数设置集合")
    @GetMapping("")
    public CommonResponse get() {
        return CommonResponse.ok(settingService.get());
    }

    @ApiOperation(value = "获取代数奖励", notes = "返回:代数奖励")
    @GetMapping("award/generation")
    public CommonResponse getGenerationAward() {
        return CommonResponse.ok(settingService.getGenerationAward());
    }

    @ApiOperation("修改代数奖励")
    @PutMapping("award/generation")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "qualifiedNum", value = "第一代合格人数", paramType = "form", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "selfAmount", value = "自购金额(单位元)", paramType = "form", dataType = "BigDecimal", required = false),
            @ApiImplicitParam(name = "generationNum", value = "享受奖励代数", paramType = "form", dataType = "Integer", required = false)
    })
    public CommonResponse<String> updateGenerationAward(@ApiIgnore GenerationAwardParameter generationAwardParameter) {
        generationAwardParameter.setUpdateUser(ShiroUtils.getShiroUser().getId());
        this.settingService.updateGenerationAward(generationAwardParameter);
        return CommonResponse.ok();
    }

    @ApiOperation(value = "获取级差奖励", notes = "返回:级差奖励")
    @GetMapping("award/level")
    public CommonResponse getLevelAward() {
        return CommonResponse.ok(settingService.getLevelAward());
    }

    @ApiOperation("修改代数奖励")
    @PutMapping("award/level")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "form", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "levelName", value = "级别名称", paramType = "form", dataType = "String", required = false),
            @ApiImplicitParam(name = "minAmt", value = "级别最小业绩", paramType = "form", dataType = "BigDecimal", required = false),
            @ApiImplicitParam(name = "maxAmt", value = "级别最大业绩", paramType = "form", dataType = "BigDecimal", required = false),
            @ApiImplicitParam(name = "prizeAec", value = "aec奖励百分比", paramType = "form", dataType = "BigDecimal", required = false),
            @ApiImplicitParam(name = "prizeMsc", value = "msc奖励百分比", paramType = "form", dataType = "BigDecimal", required = false)
    })
    public CommonResponse<String> updateLevelAward(@ApiIgnore LevelAwardParameter levelAwardParameter) {
        this.settingService.updateLevelAward(levelAwardParameter);
        return CommonResponse.ok();
    }

}
