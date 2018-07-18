package com.hengxunda.dao.po.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRecordPo {

    @ApiModelProperty(value = "钱包记录ID")
    private String id;

    @ApiModelProperty(value = "交易类型:付款、收款、收币、提币、平台拨币、换币、卖出、买入")
    private String operate;

    @ApiModelProperty(value = "交易时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "币种类型")
    private String type;

    @ApiModelProperty(value = "到账币种")
    private String receiptCurrency;

    @ApiModelProperty(value = "数量")
    private String amount;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "交易对")
    private String pair;

    @ApiModelProperty(value = "交易对象")
    private String address;

    @ApiModelProperty(value = "交易数量")
    private String receiveAmount;

    @ApiModelProperty(value = "标志位：0：amount为交易总量，receiveAmount为到账金额，1：receiveAmount为交易总量，amount为到账金额")
    private String flag;
}
