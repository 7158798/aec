package com.hengxunda.dao.po.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayInfoPo {

    @ApiModelProperty(value = "开户人姓名")
    private String name;
    @ApiModelProperty(value = "开户银行")
    private String bankName;
    @ApiModelProperty(value = "银行账户")
    private String bankNo;
    @ApiModelProperty(value = "支付宝姓名")
    private String alipayName;
    @ApiModelProperty(value = "支付宝二维码")
    private String alipayAddress;
}
