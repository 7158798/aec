package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankInfoListVo {

    @ApiModelProperty(value = "id", name = "id")
    private String id;

    @ApiModelProperty(value = "银行卡名称", name = "bankName")
    private String bankName;

    @ApiModelProperty(value = "银行卡号", name = "cardNo")
    private String cardNo;

    @ApiModelProperty(value = "状态(0.启用，1.不启用)", name = "status")
    private Integer status;

    @ApiModelProperty(value = "卡类型(0.银行卡,1.PayPal,2. 西联汇款,3. SWIFT国际汇款,4. 支付宝)", name = "type")
    private Integer type;

    @ApiModelProperty(value = "创建时间", name = "createTime")
    private String createTime;
}
