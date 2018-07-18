package com.hengxunda.app.controller;

import com.github.pagehelper.PageInfo;
import com.hengxunda.app.service.IBbAdvertService;
import com.hengxunda.common.Enum.BbTransTypeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.dao.po.app.BbAdvertPo;
import com.hengxunda.dao.po.app.BbAdvertVo;
import com.hengxunda.dao.po.app.BbTransPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 币币交易挂单Controller
 *
 * @author chengwei
 * @date 2018-07-06
 */
@RestController
@RequestMapping("/bb")
@Api(description = "币币交易挂单管理")
public class BbAdvertController {

    @Autowired
    private IBbAdvertService bbAdvertService;

    @ApiOperation("挂单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = false, paramType = "header", dataType = "string")
    })
    @GetMapping("/advert/list")
    public CommonResponse<PageInfo<BbAdvertVo>> getAdvertList(
            HttpServletRequest request,
            @ApiParam(value = "买卖类型：0.卖出，1.买入", required = true) @RequestParam Integer type,
            @ApiParam(value = "页数", required = true) @RequestParam Integer pageNo,
            @ApiParam(value = "页面大小", required = true) @RequestParam Integer pageSize) {
        A.check(type != 0 && type != 1, "买卖类型错误");
        Object tokenObj = request.getHeader("token");
        String token = "";
        if (null != tokenObj) {
            token = tokenObj.toString();
        }
        return CommonResponse.ok(bbAdvertService.queryListByPage(token, type, pageNo, pageSize));
    }

    @ApiOperation("我的挂单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true, paramType = "header", dataType = "string")
    })
    @GetMapping("/advert/list/mine")
    public CommonResponse<PageInfo<BbAdvertVo>> getMineAdvertList(
            @ApiParam(value = "页数", required = true) @RequestParam Integer pageNo,
            @ApiParam(value = "页面大小", required = true) @RequestParam Integer pageSize
    ) {
        return CommonResponse.ok(bbAdvertService.queryMineAdvertListByPage(pageNo, pageSize));
    }

    @ApiOperation("最近成交价")
    @GetMapping("/trans/last/price")
    public CommonResponse<BigDecimal> getLastPrice() {
        return CommonResponse.ok(bbAdvertService.queryLastTransPrice());
    }

    @ApiOperation("币币交易记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true, paramType = "header", dataType = "string")
    })
    @GetMapping("/trans/list")
    public CommonResponse<PageInfo<BbTransPo>> getTransList(
            @ApiParam(value = "页数", required = true) @RequestParam Integer pageNo,
            @ApiParam(value = "页面大小", required = true) @RequestParam Integer pageSize) {
        return CommonResponse.ok(bbAdvertService.queryTransListByPage(pageNo, pageSize));
    }

    @ApiOperation("新增挂单")
    @PostMapping("/advert/insert")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", paramType = "header", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "买卖类型：0.卖出，1.买入", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "price", value = "单价", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "amount", value = "数量", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "资金密码", paramType = "form", dataType = "String", required = true),
    })
    public CommonResponse<String> insert(@ApiIgnore BbAdvertPo bbAdvertPo) {
        A.check(BbTransTypeEnum.getEnum(bbAdvertPo.getType()) == null, "不支持的买卖类型");
        A.check(MathUtils.greatOrEqualForBg(BigDecimal.ZERO, bbAdvertPo.getPrice()), "单价小于等于0");
        A.check(MathUtils.greatOrEqualForBg(BigDecimal.ZERO, bbAdvertPo.getAmount()), "数量小于等于0");
        A.check(StringUtils.isEmpty(bbAdvertPo.getPassword()), "资金密码为空");
        return bbAdvertService.insertAdvert(bbAdvertPo);
    }

    @ApiOperation("币币交易")
    @PostMapping("/trans")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", paramType = "header", dataType = "string", required = true),
            @ApiImplicitParam(name = "bbAdvertId", value = "广告id", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "transAmount", value = "交易数量", paramType = "form", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "资金密码", paramType = "form", dataType = "String", required = true),
    })
    public CommonResponse<String> trans(@ApiIgnore BbTransPo bbTransPo) {
        A.check(null == bbTransPo, "请求参数为空");
        A.check(StringUtils.isEmpty(bbTransPo.getPassword()), "资金密码为空");
        A.check(StringUtils.isEmpty(bbTransPo.getBbAdvertId()), "广告id为空");
        A.check(StringUtils.isEmpty(bbTransPo.getTransAmount()), "交易数量为空");
        return bbAdvertService.trans(bbTransPo);
    }

    @ApiOperation("删除挂单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true, paramType = "header", dataType = "string")
    })
    @GetMapping("/advert/delete")
    public CommonResponse<String> delete(@ApiParam(value = "挂单id", required = true) @RequestParam String id) {
        A.check(StringUtils.isEmpty(id), "挂单id为空");
        return bbAdvertService.deleteAdvert(id);
    }
}
