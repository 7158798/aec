package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AppVersion {
    private String id;

    private Integer source;

    private Integer osType;

    private Integer code;

    private String value;

    private Boolean forceUpdate;

    private String downloadLink;

    private String comment;

    private Date createTime;


}