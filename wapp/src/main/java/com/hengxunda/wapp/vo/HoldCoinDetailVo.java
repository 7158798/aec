package com.hengxunda.wapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HoldCoinDetailVo {

    @ApiModelProperty("币种")
    private String coinType;

    @ApiModelProperty("可用金额")
    private String balance;

    @ApiModelProperty("冻结金额")
    private String frozenBlance;
}
