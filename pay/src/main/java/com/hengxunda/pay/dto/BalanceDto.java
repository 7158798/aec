package com.hengxunda.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 查询余额实体类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BalanceDto {

    @ApiModelProperty(notes = "用户账户", name = "uid" ,example = "18688775132")
    private String uid;

    @ApiModelProperty(notes = "密码", name = "password" ,example = "123456")
    private String password;

    @ApiModelProperty(notes = "币种", name = "type" ,example = "AEC")
    private String type;
}
