package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MscRecord {
    private String id;

    private String userId;

    private String walletRecordId;

    private BigDecimal aecAmount;

    private BigDecimal mscAmount;

    private BigDecimal restMscAmount;

    private Integer status;

    private Integer type;

    private Integer effectStatus;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

}