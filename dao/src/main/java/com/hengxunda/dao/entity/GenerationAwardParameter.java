package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GenerationAwardParameter {
    private String id;

    private Integer qualifiedNum;

    private BigDecimal selfAmount;

    private Integer generationNum;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}