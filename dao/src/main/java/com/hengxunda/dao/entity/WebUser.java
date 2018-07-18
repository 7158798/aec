package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WebUser {
    private String id;

    private String userName;

    private String userNick;

    private String phone;

    private String email;

    private Integer status;

    private String password;

    private String payPassword;

    private String token;

    private Date tokenExpireTime;

    private Date createTime;

    private Date updateTime;


}