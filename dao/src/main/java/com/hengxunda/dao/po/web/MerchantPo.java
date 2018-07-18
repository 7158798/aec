package com.hengxunda.dao.po.web;

import com.hengxunda.dao.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantPo extends User {

    private BigDecimal bond;

    private BigDecimal balance;

    private Date createBeginTime;

    private Date createEndTime;
}
