package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NoticeUserLook {
    private String id;

    private String userId;

    private String noticeId;

    private Integer status;

    private Date createTime;

}