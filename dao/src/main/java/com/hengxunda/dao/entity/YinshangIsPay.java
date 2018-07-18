package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class YinshangIsPay {
    private String id;

    private String userId;

    private Integer cny;

    private Integer usd;

    private Integer eur;

    private Integer hkd;

    private Integer alipay;

    private Integer paypal;

    private Integer xilian;

    private Integer swift;

    private Integer weixin;

    private Date createTime;

    private String upateUser;

    private Date updateTime;


}