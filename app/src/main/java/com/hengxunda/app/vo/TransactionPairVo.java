package com.hengxunda.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionPairVo {
    @ApiModelProperty(value = "交易对")
    private String transactionPair;
    @ApiModelProperty(value = "行情")
    private String quotation;
    @ApiModelProperty(value = "行情是否用，0.可用，1.不可用")
    private int isUse;
}
