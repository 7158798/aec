package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserSms {
    private String id;

    private String phone;

    private Integer type;

    private String code;

    private Integer status;

    private Date createTime;


}