package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {

    @ApiModelProperty(value = "银商id", name = "userId")
    private String userId;

    @ApiModelProperty(value = "名称", name = "name")
    private String name;

    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;

    @ApiModelProperty(value = "头像", name = "avatar")
    private String avatar;

    @ApiModelProperty(value = "余额", name = "balance")
    private BigDecimal balance;

    @ApiModelProperty(value = "保证金", name = "bond")
    private BigDecimal bond;

    @ApiModelProperty(value = "交易次数", name = "bond")
    private Integer tradingNumber;


}
