package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayVo {

    @ApiModelProperty(value = "名称", name = "name")
    private String name;

    @ApiModelProperty(value = "是否设置支付方式(true.已设置，false.未设置)", name = "pay")
    private boolean pay;

}