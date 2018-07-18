package com.hengxunda.wapp.controller;

import com.google.common.collect.Lists;
import com.hengxunda.common.Enum.BankTypeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.dao.entity.UserBankInfo;
import com.hengxunda.dao.entity.UserReceive;
import com.hengxunda.wapp.dto.BankInfoDto;
import com.hengxunda.wapp.dto.CardStatusDto;
import com.hengxunda.wapp.dto.UserReceiveDto;
import com.hengxunda.wapp.service.IBankInfoService;
import com.hengxunda.wapp.service.IUserReceiveService;
import com.hengxunda.wapp.vo.BankInfoListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/bank")
@Api(value = "/bank", description = "银行卡管理")
public class BankInfoController {

    @Autowired
    IBankInfoService bankInfoService;

    @Autowired
    IUserReceiveService userReceiveService;

    @ApiOperation("添加银行卡")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PostMapping("addBank")
    public CommonResponse addBank(@RequestBody BankInfoDto bankInfoDto) {
        switch (BankTypeEnum.acquireByCode(bankInfoDto.getType())) {
            case BANK_NO:
                A.check(StringUtils.isBlank(bankInfoDto.getName()), "姓名不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankName()), "开户银行不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankAddress()), "开户地址不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankNo()), "银行卡号不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getCurrency()), "请至少选择一个支持币种");
                A.check(StringUtils.isBlank(bankInfoDto.getPayPass()), "资金密码不能为空");
                break;
            case PAYPAL:
            case WESTERN_UNION:
            case SWIFT:
                A.check(StringUtils.isBlank(bankInfoDto.getName()), "姓名不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankNo()), "账号不能为空");
                break;
        }
        bankInfoService.insert(bankInfoDto);
        return CommonResponse.ok();
    }

    @ApiOperation("编辑银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "cardStatusDto", value = "修改卡状态实体类", required = true, paramType = "body", dataType = "CardStatusDto"),
    })
    @PutMapping("editBank")
    public CommonResponse editBank(@RequestBody CardStatusDto cardStatusDto) {
        bankInfoService.update(cardStatusDto);
        return CommonResponse.ok();
    }

    @ApiOperation("添加支付宝")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PostMapping("addAlipay")
    public CommonResponse addAlipay(@RequestBody UserReceiveDto userReceiveDto) {
        A.check(StringUtils.isBlank(userReceiveDto.getName()), "姓名不能为空");
        A.check(StringUtils.isBlank(userReceiveDto.getNo()), "支付宝账号不能为空");
        A.check(StringUtils.isBlank(userReceiveDto.getAddress()), "收款二维码地址不能为空");
        A.check(StringUtils.isBlank(userReceiveDto.getPayPass()), "资金密码不能为空");
        userReceiveService.insert(userReceiveDto);
        return CommonResponse.ok();
    }

    @ApiOperation("编辑支付宝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "cardStatusDto", value = "修改卡状态实体类", required = true, paramType = "body", dataType = "CardStatusDto"),
    })
    @PutMapping("editAlipay")
    public CommonResponse editAlipay(@RequestBody CardStatusDto cardStatusDto) {
        userReceiveService.update(cardStatusDto);
        return CommonResponse.ok();
    }

    @GetMapping("list")
    @ApiOperation(value = "获取银行卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    })
    public CommonResponse<List<BankInfoListVo>> list() {

        return CommonResponse.ok(list(bankInfoService.findList(), userReceiveService.findList()));
    }

    private List<BankInfoListVo> list(List<UserBankInfo> userBankInfos, List<UserReceive> userReceives) {
        List<BankInfoListVo> bankInfoListVos = Lists.newArrayList();
        userBankInfos.stream().forEach(userBankInfo -> {
            bankInfoListVos.add(BankInfoListVo.builder().id(userBankInfo.getId()).createTime(DateUtils.format(userBankInfo.getCreateTime())).bankName(userBankInfo.getBankName()).cardNo(userBankInfo.getBankNo()).status(userBankInfo.getStatus())
                    .type(userBankInfo.getType())
                    .build());
        });

        userReceives.stream().forEach(userReceive -> {
            bankInfoListVos.add(BankInfoListVo.builder().id(userReceive.getId()).createTime(DateUtils.format(userReceive.getCreateTime())).bankName("支付宝").cardNo(userReceive.getNo()).status(userReceive.getStatus()).type(BankTypeEnum.ALIPAY.getCode()).build());
        });
        bankInfoListVos.sort(Comparator.comparing(BankInfoListVo::getCreateTime).reversed());
        return bankInfoListVos;
    }
}
