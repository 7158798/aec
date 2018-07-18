package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class YinshangApply {
    private String id;

    private Integer dataFlagStatus;

    private String userId;

    private Integer status;

    private String reason;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}