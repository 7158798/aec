package com.hengxunda.dao.po.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BbTransPo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "币币交易编码")
    private String bbNo;

    @ApiModelProperty(value = "币币交易广告id")
    private String bbAdvertId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "买卖类型：0.卖出，1.买入")
    private String type;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "交易数量")
    private BigDecimal transAmount;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "交易密码")
    private String password;

}