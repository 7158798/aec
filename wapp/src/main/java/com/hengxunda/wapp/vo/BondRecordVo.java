package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BondRecordVo {

    @ApiModelProperty(value = "title", name = "title")
    private String title;

    @ApiModelProperty(value = "金额", name = "amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "状态(1: + , 2: -)", name = "status")
    private Integer status;

    @ApiModelProperty(value = "创建时间", name = "createTime")
    private String createTime;

}
