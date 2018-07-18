package com.hengxunda.dao.entity;

import com.hengxunda.common.utils.UUIDUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GlobalParameter {
    private String id;

    private String cronKey;

    private String cronValue;

    private String minValue;

    private String groupName;

    private String descri;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;


    public GlobalParameter(String cronKey, String cronValue, String groupName, String descri, String createUser, Date createTime) {
        this.id = UUIDUtils.getUUID();
        this.cronKey = cronKey;
        this.cronValue = cronValue;
        this.groupName = groupName;
        this.descri = descri;
        this.createUser = createUser;
        this.createTime = createTime;
    }
}