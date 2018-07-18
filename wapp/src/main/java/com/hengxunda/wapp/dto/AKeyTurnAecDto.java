package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 一键转币（其他币种转AEC)实体类DTO
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AKeyTurnAecDto {

    @ApiModelProperty(notes = "btc数量", name = "btcAmount", example = "0.1")
    private BigDecimal btcAmount;

    @ApiModelProperty(notes = "btc钱包地址", name = "btcAddress", example = "btc11111111111")
    private String btcAddress;

    @ApiModelProperty(notes = "BTC折合AEC数量", name = "aec2btcAmount", example = "0.1")
    private BigDecimal aec2btcAmount;

    @ApiModelProperty(notes = "ltc数量", name = "ltcAmount", example = "0.1")
    private BigDecimal ltcAmount;

    @ApiModelProperty(notes = "ltc钱包地址", name = "ltcAddress", example = "ltc111111111")
    private String ltcAddress;

    @ApiModelProperty(notes = "LTC折合AEC数量", name = "aec2ltcAmount", example = "0.1")
    private BigDecimal aec2ltcAmount;

    @ApiModelProperty(notes = "aec钱包地址", name = "aecAddress", example = "aec111111111111")
    private String aecAddress;

    @ApiModelProperty(notes = "交易密码", name = "fundPasswd", example = "123456")
    private String fundPasswd;

    @ApiModelProperty(notes = "验证码", name = "captchCode", example = "123456")
    private String captchCode;
}
