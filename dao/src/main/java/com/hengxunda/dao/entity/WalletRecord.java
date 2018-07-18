package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRecord {
    private String id;

    private String transactionPair;

    private String fromId;

    private String toId;

    private String fromAddress;

    private String toAddress;

    private BigDecimal fromAmount;

    private BigDecimal toAmount;

    private BigDecimal fee;

    private String rate;

    private Integer operate;

    private String descri;

    private String source;

    private Integer transType;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


}