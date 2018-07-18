package com.hengxunda.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2018/6/22.
 */

@Getter
@Setter
@Accessors(chain = true)
public class AddressStatisticsVo {
    @ApiModelProperty("ETH，BTC，LTC")
    private String type;
    @ApiModelProperty("剩余")
    private Long surplusAddress;
    @ApiModelProperty("总数")
    private Long tolalAddress;
}
