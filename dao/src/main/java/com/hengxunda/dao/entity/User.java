package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private String id;

    private String name;

    private String uid;

    private String nickName;

    private String nationalCode;

    private String phone;

    private String email;

    private Integer gender;

    private String avatar;

    private Integer status;

    private Integer level;

    private Integer role;

    private String password;

    private String salt;

    private String payPassword;

    private String paySalt;

    private String idCard;

    private Integer loginStatus;

    private Integer isShop;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private BigDecimal selfAchievement;

    private BigDecimal fullAchievement;

    private Integer qualifiedNum;

}