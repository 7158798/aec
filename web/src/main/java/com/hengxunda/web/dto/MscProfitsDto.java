package com.hengxunda.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MscProfitsDto {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("描述")
    private String descri;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateUser;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}