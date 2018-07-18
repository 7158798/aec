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
public class DialCoinPo {
    private String id;
    private String orderNo;
    private String name;
    private String phone;
    @ApiModelProperty("类型，0.平台拨币,10.差额奖励,12.股权分红，13.代数奖励")
    private int type;
    @ApiModelProperty("0：未锁仓，1：锁仓")
    private int status;
    private String coin;
    private BigDecimal amount;
    private Date createTime;
}
