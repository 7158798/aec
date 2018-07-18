package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SyncBlock {
    private String id;

    private String code;

    private String value;

    private String descri;

    private Date createTime;

    private Date updateTime;

}