package com.hengxunda.dao.po.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BankInfoPo {

    @ApiModelProperty(value = "开户人姓名")
    private String name;
    @ApiModelProperty(value = "开户银行")
    private String bankName;
    @ApiModelProperty(value = "银行账户")
    private String bankNo;
    @ApiModelProperty(value = "0.银行卡,1.PayPal,2. 西联汇款,3. SWIFT国际汇款")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;
}
