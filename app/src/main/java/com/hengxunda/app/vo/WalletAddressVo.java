package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 钱包请求对象
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletAddressVo {

    @ApiModelProperty(value = "钱包址址")
    private String address;
    @ApiModelProperty(value = "钱包类型，BTC,LTC,AEC,MSC")
    private String type;

}
