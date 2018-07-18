package com.hengxunda.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserRecommend {
    private String id;

    private String recommendId;

    private String refereeId;

    private Date createTime;


}