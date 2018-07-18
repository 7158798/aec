package com.hengxunda.app.controller;

import com.hengxunda.app.service.IOssService;
import com.hengxunda.app.vo.OssInfoVo;
import com.hengxunda.common.utils.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "OSS图片上传")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private IOssService iOssService;

    @ApiOperation("oss图片上传")
    @GetMapping("/ossInfo")
    public CommonResponse<OssInfoVo> ossInfo(){
        return CommonResponse.ok(iOssService.ossInfo());
    }
}
