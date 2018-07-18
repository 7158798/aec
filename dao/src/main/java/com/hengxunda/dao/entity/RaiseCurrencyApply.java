package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RaiseCurrencyApply {
    private String id;

    private String userId;

    private String fromAddress;

    private String toAddress;

    private BigDecimal amount;

    private Integer status;

    private String reason;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;



}