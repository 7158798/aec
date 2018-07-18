package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertDto implements Serializable {

    @ApiModelProperty(value = "广告id", name = "id", example = " ")
    private String id;

    @ApiModelProperty(value = "交易类型(0.卖出，1.买入)", name = "type", example = "0")
    private Integer type;

    @ApiModelProperty(value = "单价", name = "unitPrice", example = "0.9")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "最小值", name = "minValue", example = "1000")
    private BigDecimal minValue;

    @ApiModelProperty(value = "最大值", name = "maxValue", example = "5000")
    private BigDecimal maxValue;

    @ApiModelProperty(value = "广告状态(0.上架，1.下架)", name = "status", example = "0")
    private Integer status;

}
