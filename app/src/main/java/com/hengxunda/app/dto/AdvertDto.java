package com.hengxunda.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertDto implements Serializable {

    @ApiModelProperty(notes = "广告id", name = "id", example = " ")
    private String id;

    @ApiModelProperty(notes = "交易类型(0.卖出，1.买入)", name = "type", example = "0")
    private String type;

    @ApiModelProperty(notes = "单价", name = "unitPrice", example = "0.9")
    private String unitPrice;

    @ApiModelProperty(notes = "最小值", name = "minValue", example = "1000")
    private String minValue;

    @ApiModelProperty(notes = "最大值", name = "maxValue", example = "5000")
    private String maxValue;

    @ApiModelProperty(notes = "广告状态(0.上架，1.下架)", name = "status", example = "0")
    private String status;

}
