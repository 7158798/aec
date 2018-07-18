package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AppealDto {
    @ApiModelProperty(value = "订单id")
    private String orderId;
    @ApiModelProperty(value = "申诉类型id")
    private String appealTypeId;
    @ApiModelProperty(value = "申诉内容")
    private String content;
    @ApiModelProperty(value = "图片地址，多张以逗号隔开")
    private String url;
}
