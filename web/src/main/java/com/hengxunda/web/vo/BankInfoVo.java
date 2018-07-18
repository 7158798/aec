package com.hengxunda.web.vo;

import com.hengxunda.dao.entity.UserBankInfo;
import com.hengxunda.dao.entity.UserReceive;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BankInfoVo {
    private String id;

    @ApiModelProperty("0:银行卡，1：支付宝，2：微信")
    private int type;

    private String userId;

    private String name;

    @ApiModelProperty("支付方式为扫码时此字段表示二维码url")
    private String bankName;

    private String bankAddress;

    @ApiModelProperty("支付方式为扫码时此字段表示账号")
    private String bankNo;

    @ApiModelProperty("状态：0.启用，1.不启用，2.删除")
    private Integer status;

    @ApiModelProperty("是否支持该币种：0.支持，1.不支持，下面四个用法一样")
    private Integer cny;

    private Integer usd;

    private Integer eur;

    private Integer hkd;

    @ApiModelProperty("备注")
    private String remark;

    public static BankInfoVo formatBank(UserBankInfo userBankInfo){
        BankInfoVo bankInfoVo = new BankInfoVo();
        bankInfoVo.setId(userBankInfo.getId()).setType(0).setUserId(userBankInfo.getUserId()).setRemark(userBankInfo.getRemark())
                .setName(userBankInfo.getName()).setBankName(userBankInfo.getBankName()).setBankAddress(userBankInfo.getBankAddress())
                .setBankNo(userBankInfo.getBankNo()).setStatus(userBankInfo.getStatus()).setCny(userBankInfo.getCny())
                .setUsd(userBankInfo.getUsd()).setEur(userBankInfo.getEur()).setHkd(userBankInfo.getHkd());
        return bankInfoVo;
    }
    public static List<BankInfoVo> formatBankList(List<UserBankInfo> list){
        List<BankInfoVo> result = new ArrayList<>();
        for(UserBankInfo userBankInfo:list){
            result.add(formatBank(userBankInfo));
        }
        return result;
    }

    public static BankInfoVo formatReceive(UserReceive userReceive){
        BankInfoVo bankInfoVo = new BankInfoVo();
        bankInfoVo.setId(userReceive.getId()).setType(userReceive.getType()==0?1:2).setUserId(userReceive.getUserId()).setRemark(userReceive.getRemark())
                .setName(userReceive.getName()).setBankNo(userReceive.getNo()).setBankName(userReceive.getAddress());

        return bankInfoVo;
    }
    public static List<BankInfoVo> formatReceiveList(List<UserReceive> list){
        List<BankInfoVo> result = new ArrayList<>();
        for(UserReceive userReceive:list){
            result.add(formatReceive(userReceive));
        }
        return result;
    }


//    private Date createTime;
//
//    private String updateUser;
//
//    private Date updateTime;
}
