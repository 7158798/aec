package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Order {
    private String id;

    private String transactionPair;

    private Integer type;

    private String orderNo;

    private String adveruUserId;

    private String advertId;

    private String userId;

    private BigDecimal unitPrice;

    private BigDecimal quantity;

    private BigDecimal money;

    private String taxRate;

    private Integer cancelStatus;

    private Integer appealStatus;

    private Integer status;

    private Integer role;

    private Date createTime;

    private Date confirmTime;

    private String updateUser;

    private Date updateTime;

    private String appealUser;

    private Date appealTime;
}