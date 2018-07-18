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
public class BbAdvertVo {

    @ApiModelProperty(value = "币币交易广告id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "买卖类型：0.卖出，1.买入")
    private Integer type;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "发布数量")
    private String amount;

    @ApiModelProperty(value = "剩余数量")
    private String restAmount;

    @ApiModelProperty(value = "状态：0.正常，1.已删除")
    private Integer status;

    @ApiModelProperty(value = "是否为自己的广告:0.是，1.否")
    private Integer selfInd;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "资金密码")
    private String password;

}