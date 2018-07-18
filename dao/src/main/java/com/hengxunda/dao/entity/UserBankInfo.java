package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserBankInfo {
    private String id;

    private Integer type;

    private String userId;

    private String name;

    private String bankName;

    private String bankAddress;

    private String bankNo;

    private Integer status;

    private Integer cny;

    private Integer usd;

    private Integer eur;

    private Integer hkd;

    private String remark;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}