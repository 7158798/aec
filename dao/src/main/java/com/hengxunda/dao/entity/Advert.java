package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Advert {
    private String id;

    private Integer type;

    private BigDecimal stock;

    private BigDecimal enableStock;

    private BigDecimal unitPrice;

    private Integer isLimit;

    private BigDecimal minValue;

    private BigDecimal maxValue;

    private Integer status;

    private Integer cny;

    private Integer usd;

    private Integer eur;

    private Integer hkd;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


}