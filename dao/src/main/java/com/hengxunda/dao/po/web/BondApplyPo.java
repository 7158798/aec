package com.hengxunda.dao.po.web;

import com.hengxunda.dao.entity.BondApply;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/9
 */
@Data
@Accessors(chain = true)
public class BondApplyPo extends BondApply {
    private String name;
    private String phone;
    private Date beginTime;
    private Date endTime;
}
