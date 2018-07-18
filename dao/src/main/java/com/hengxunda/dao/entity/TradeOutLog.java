package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TradeOutLog {
    private String id;

    private String shopuserid;

    private String userid;

    private String billcode;

    private String ordno;

    private String tradetype;

    private String cointype;

    private BigDecimal amount;

    private String status;

    private Date createtime;

    private Date lasttime;
}