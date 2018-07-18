package com.hengxunda.web.controller;

import com.github.pagehelper.PageInfo;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.po.web.MscProfitsPo;
import com.hengxunda.web.service.IMscProfitsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 股权分红
 *
 * @author chengwei
 * @date 2018-06-07
 */
@Slf4j
@Api(description = "股权分红")
@RestController
@RequestMapping("msc/profits")
public class MscProfitsController {
    @Autowired
    private IMscProfitsService mscProfitsService;

    @ApiOperation("分页获取股权分红列表")
    @GetMapping("/list")
    public CommonResponse<PageInfo<MscProfitsPo>> getList(
            @ApiParam(value = "页数") @RequestParam Integer pageNo,
            @ApiParam(value = "页面大小") @RequestParam Integer pageSize) {
        return CommonResponse.ok(mscProfitsService.queryListByPage(pageNo, pageSize));
    }

    @ApiOperation("新增分红")
    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "amount", value = "分红金额", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "descri", value = "分红描述", paramType = "form", dataType = "String", required = false)
    })
    public CommonResponse<String> add(@ApiIgnore MscProfitsPo mscProfitsPo) {
        mscProfitsService.addMscProfits(mscProfitsPo);
        return CommonResponse.ok("保存成功");
    }

    @ApiOperation("修改分红")
    @PostMapping("/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "amount", value = "分红金额", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "descri", value = "分红描述", paramType = "form", dataType = "String", required = false)
    })
    public CommonResponse<String> update(@ApiIgnore MscProfitsPo mscProfitsPo) {
        return mscProfitsService.updateMscProfits(mscProfitsPo);
    }

    @ApiOperation("删除分红记录")
    @PostMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "form", dataType = "String", required = true)
    })
    public CommonResponse<String> delete(@ApiIgnore MscProfitsPo mscProfitsPo) {
        return mscProfitsService.deleteMscProfits(mscProfitsPo);
    }

    @ApiOperation("分红")
    @PostMapping("/allot")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "form", dataType = "String", required = true)
    })
    public CommonResponse<String> allotProfits(@ApiIgnore MscProfitsPo mscProfitsPo) {
        return mscProfitsService.allotMscProfits(mscProfitsPo);
    }
}
