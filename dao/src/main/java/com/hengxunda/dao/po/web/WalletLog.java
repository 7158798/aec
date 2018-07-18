package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletLog {
    private String id;
    private String orderNo;
    private String name;
    private String phone;
    @ApiModelProperty("0：充币，1：提币")
    private int type;
    @ApiModelProperty("0：收币成功，1：提币成功")
    private int status;
    private BigDecimal amount;
    private String address;
    private String remark;
    private Date createTime;
    @ApiModelProperty("系统内部使用，前端不必考虑")
    private String coinName;

}
