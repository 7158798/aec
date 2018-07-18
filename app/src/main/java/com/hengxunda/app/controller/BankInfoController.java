package com.hengxunda.app.controller;

import com.google.common.collect.Lists;
import com.hengxunda.app.dto.BankInfoDto;
import com.hengxunda.app.dto.CardStatusDto;
import com.hengxunda.app.dto.UserReceiveDto;
import com.hengxunda.app.service.IBankInfoService;
import com.hengxunda.app.service.IUserReceiveService;
import com.hengxunda.app.vo.BankInfoListVo;
import com.hengxunda.common.Enum.BankTypeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.dao.entity.UserBankInfo;
import com.hengxunda.dao.entity.UserReceive;
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
@Api(description = "银行卡管理")
public class BankInfoController {

    @Autowired
    IBankInfoService bankInfoService;

    @Autowired
    IUserReceiveService userReceiveService;

    @ApiOperation("添加银行卡")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PostMapping("/addBank")
    public CommonResponse addBank(@RequestBody BankInfoDto bankInfoDto) {
        check(bankInfoDto);
        bankInfoService.insert(bankInfoDto);
        return CommonResponse.ok();
    }

    private void check(BankInfoDto bankInfoDto) {
        switch (bankInfoDto.getType()) {
            case 0:
                A.check(StringUtils.isBlank(bankInfoDto.getName()), "姓名不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankName()), "开户银行不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankAddress()), "开户地址不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankNo()), "银行卡号不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getCurrency()), "请至少选择一个支持币种");
                A.check(StringUtils.isBlank(bankInfoDto.getPayPass()), "资金密码不能为空");
                break;
            case 1:
            case 2:
            case 3:
                A.check(StringUtils.isBlank(bankInfoDto.getName()), "姓名不能为空");
                A.check(StringUtils.isBlank(bankInfoDto.getBankNo()), "账号不能为空");
                break;
        }


    }

    @ApiOperation("添加支付宝")
    @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    @PostMapping("/addAlipay")
    public CommonResponse addAlipay(@RequestBody UserReceiveDto userReceiveDto) {
        A.check(StringUtils.isBlank(userReceiveDto.getName()), "姓名不能为空");
        A.check(StringUtils.isBlank(userReceiveDto.getNo()), "支付宝账号不能为空");
        A.check(StringUtils.isBlank(userReceiveDto.getAddress()), "收款二维码地址不能为空");
        A.check(StringUtils.isBlank(userReceiveDto.getPayPass()), "资金密码不能为空");
        userReceiveService.insert(userReceiveDto);
        return CommonResponse.ok();
    }

    @ApiOperation("修改银行卡状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "cardStatusDto", value = "修改卡状态实体类", required = true, paramType = "body", dataType = "CardStatusDto"),
    })
    @PutMapping("/bankStatus")
    public CommonResponse updateBankStatus(@RequestBody CardStatusDto cardStatusDto) {
        bankInfoService.updateStatus(cardStatusDto);
        return CommonResponse.ok();
    }

    @ApiOperation("修改支付宝状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "cardStatusDto", value = "修改卡状态实体类", required = true, paramType = "body", dataType = "CardStatusDto"),
    })
    @PutMapping("/aliPayStatus")
    public CommonResponse updateAlipayStatus(@RequestBody CardStatusDto cardStatusDto) {
        userReceiveService.updateStatus(cardStatusDto);
        return CommonResponse.ok();
    }


    @ApiOperation(value = "获取银行卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true, paramType = "header", dataType = "String")
    })
    @GetMapping("list")
    public CommonResponse<List<BankInfoListVo>> list() {

        return CommonResponse.ok(list(bankInfoService.findList(), userReceiveService.findList()));
    }

    private List<BankInfoListVo> list(List<UserBankInfo> userBankInfos, List<UserReceive> userReceives) {
        List<BankInfoListVo> bankInfoListVos = Lists.newArrayList();
        userBankInfos.stream().forEach(userBankInfo -> {
            bankInfoListVos.add(BankInfoListVo.builder().id(userBankInfo.getId()).bankName(userBankInfo.getBankName()).cardNo(userBankInfo.getBankNo()).status(userBankInfo.getStatus()).createTime(DateUtils.format(userBankInfo.getCreateTime()))
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
