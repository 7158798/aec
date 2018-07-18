package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserReceive {
    private String id;

    private String userId;

    private Integer type;

    private String name;

    private String no;

    private String address;

    private Integer status;

    private String remark;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


}