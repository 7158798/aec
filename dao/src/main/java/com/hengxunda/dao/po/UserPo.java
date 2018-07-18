package com.hengxunda.dao.po;

import com.hengxunda.dao.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
@Data
@Accessors(chain = true)
public class UserPo extends User {
    private String recommendPhone;
    private Date createBeginTime;
    private Date createEndTime;
}
