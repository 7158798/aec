package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BbAdvert {
    private String id;

    private String userId;

    private Integer type;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal restAmount;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}