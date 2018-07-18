package com.hengxunda.dao.po.web;

import com.hengxunda.dao.entity.YinshangApply;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/8
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantApplyPo extends YinshangApply {
    private String phone;
    private String name;
    private String uid;
    private BigDecimal bond;
    private Date beginTime;
    private Date endTime;

}
