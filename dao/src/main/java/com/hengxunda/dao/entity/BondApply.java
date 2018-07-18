package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BondApply {
    private String id;

    private Integer type;

    private String userId;

    private BigDecimal totalBond;

    private BigDecimal bond;

    private Integer status;

    private String reason;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}