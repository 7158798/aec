package com.hengxunda.wapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BankInfoDto implements Serializable {

    @ApiModelProperty(value = "卡类型", name = "type", example = "0")
    private Integer type;

    @ApiModelProperty(value = "姓名", name = "name", example = "Rain")
    private String name;

    @ApiModelProperty(value = "开户银行", name = "bankName", example = "平安银行")
    private String bankName;

    @ApiModelProperty(value = "开户地址", name = "bankAddress", example = "深圳市福田区车公庙平安银行支行")
    private String bankAddress;

    @ApiModelProperty(value = "银行卡号", name = "bankNo", example = "6217002710000684874")
    private String bankNo;

    @ApiModelProperty(value = "支持币种(0:CNY,1:USD,2:EUR,3:HKD),如果支持多币种请以,分割传参", name = "currency", example = "0,1,2")
    private String currency;

    @ApiModelProperty(value = "支付密码", name = "payPass", example = "123456")
    private String payPass;

    @ApiModelProperty(value = "备注", name = "remark", example = "这是我添加的银行卡哦")
    private String remark;

}
