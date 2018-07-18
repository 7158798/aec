package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderAppeal {
    private String id;

    private String orderId;

    private String appealId;

    private String appealContent;

    private String userId;

    private String img;

    private Integer status;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String content;

}