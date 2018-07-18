package com.hengxunda.dao.po.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.PipedReader;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AlipayInfoPo {
    @ApiModelProperty(value = "支付宝姓名")
    private String alipayName;
    @ApiModelProperty(value = "支付宝二维码")
    private String alipayAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "账号")
    private String no;
}
