package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BbTrans {
    private String id;

    private String bbNo;

    private String bbAdvertId;

    private String userId;

    private BigDecimal unitPrice;

    private BigDecimal transAmount;

    private BigDecimal totalPrice;

    private Date createTime;

    private Date updateTime;

}