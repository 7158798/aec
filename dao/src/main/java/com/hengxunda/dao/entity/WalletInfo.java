package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletInfo {
    private String id;

    private String userId;

    private Integer status;

    private String type;

    private BigDecimal balance;

    private BigDecimal frozenBlance;

    private BigDecimal bond;

    private String address;

    private String walletPassword;

    private String walletFile;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


}