package com.hengxunda.wapp.controller;

import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.wapp.vo.HelpVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help")
@Api(value = "/help", description = "帮助中心")
public class HelpController {

    @Value("${html.domain.url}")
    private String domain;

    @ApiOperation("帮助中心")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @GetMapping("programed")
    public CommonResponse programed() {

        return CommonResponse.ok(HelpVo.builder(domain));
    }

}
