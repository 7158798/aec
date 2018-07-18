package com.hengxunda.wapp.controller;

import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.wapp.service.IBondService;
import com.hengxunda.wapp.vo.BondRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bond")
@Api(value = "/bond", description = "保证金管理")
public class BondController {

    @Autowired
    IBondService bondService;

    @ApiOperation("提升保证金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "amount", value = "需要提升的aec金额", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("promoteBond")
    public CommonResponse promoteBond(@RequestParam("amount") BigDecimal amount) {
        A.check(MathUtils.equalForBg(amount, BigDecimal.ZERO), "需要提升的aec金额必须大于0");
        bondService.promoteBond(amount);
        return CommonResponse.ok();
    }

    @ApiOperation("降低保证金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "amount", value = "需要降低的aec金额", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("reduceBond")
    public CommonResponse reduceBond(@RequestParam("amount") BigDecimal amount) {
        A.check(MathUtils.lessForBg(amount, BigDecimal.ONE), "可降额度不能小于1");
        bondService.reduceBond(amount);
        return CommonResponse.ok();
    }

    @ApiOperation("提取保证金")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PutMapping("extractBond")
    public CommonResponse extractBond() {
        bondService.extractBond();
        return CommonResponse.ok();
    }

    @GetMapping("record")
    @ApiOperation(value = "获取保证金记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "当前页(默认第1页)", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条记录数(默认每页15条)", paramType = "query", dataType = "Long")
    })
    public CommonResponse<List<BondRecordVo>> record(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize) {

        return CommonResponse.ok(bondService.record(pageNo, pageSize));
    }

    @GetMapping("canDrop")
    @ApiOperation(value = "获取保证金可降额度")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    public CommonResponse canDrop() {

        return CommonResponse.ok(bondService.canDrop());
    }
}
