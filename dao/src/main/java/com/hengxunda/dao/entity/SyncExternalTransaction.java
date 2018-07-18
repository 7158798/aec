package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SyncExternalTransaction {
    private String id;

    private String type;

    private String txHash;

    private String fromAddress;

    private String toAddress;

    private BigDecimal amount;

    private BigDecimal fee;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


}