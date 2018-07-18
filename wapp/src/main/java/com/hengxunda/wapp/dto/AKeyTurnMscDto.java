package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 一键转币（AEC2MSC）实体类DTO
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AKeyTurnMscDto {

    @ApiModelProperty(notes = "aec数量", name = "aecAmount", example = "0.2")
    private BigDecimal aecAmount;

    @ApiModelProperty(notes = "aec钱包地址", name = "aecAddress", example = "0.2")
    private String aecAddress;

    @ApiModelProperty(notes = "msc数量", name = "mscAmount", example = "0.1")
    private BigDecimal mscAmount;

    @ApiModelProperty(notes = "msc钱包地址", name = "mscAddress", example = "0.1")
    private String mscAddress;

    @ApiModelProperty(notes = "交易密码", name = "fundPasswd", example = "123456")
    private String fundPasswd;

    @ApiModelProperty(notes = "验证码", name = "captchCode", example = "123456")
    private String captchCode;
}
