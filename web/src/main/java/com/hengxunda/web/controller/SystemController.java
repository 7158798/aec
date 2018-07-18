package com.hengxunda.web.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.web.service.ISystemInfoService;
import com.hengxunda.web.vo.SystemConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Slf4j
@Api(description = "系统设置")
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private ISystemInfoService iSystemInfoService;

    @ApiOperation("获取当前系统设置信息")
    @PostMapping("/config/info")
    public CommonResponse<SystemConfigVo> getConfigInfo(){

        SystemConfigVo systemConfigVo = iSystemInfoService.getSystemInfo();
        return CommonResponse.ok(systemConfigVo);

    }

    @ApiOperation("设置系统开关")
    @PostMapping("/config/set")
    public CommonResponse reviewBondApply(
            @ApiParam(value = "代数奖励开关  0：关闭，1：打开", required = true)@RequestParam int GRS,
            @ApiParam(value = "级差奖励开关 0：关闭，1：打开", required = true)@RequestParam int LRS,
            @ApiParam(value = "提现开关 0：关闭，1：打开)", required = true)@RequestParam int WS
    ){

        try{
            int result = iSystemInfoService.updateSystemConfig(GRS, LRS, WS);
            if(result == 1) {
                return CommonResponse.ok("设置成功");
            }
        }catch (Exception e){
            return CommonResponse.error(e.getMessage());
        }
        return null;

    }
}
