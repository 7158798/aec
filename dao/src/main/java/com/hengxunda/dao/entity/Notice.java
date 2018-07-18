package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Notice {
    private String id;

    private String userId;

    private String noticeTypeId;

    private String title;

    private String summary;

    private String url;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String content;


}