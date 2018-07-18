package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MscProfits {
    private String id;

    private BigDecimal amount;

    private Integer status;

    private String descri;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}