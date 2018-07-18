package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserLogin {
    private String id;

    private String userId;

    private Integer source;

    private String token;

    private Date expireTime;

    private Date loginTime;

    private Integer status;

    private Long loginCnt;


}