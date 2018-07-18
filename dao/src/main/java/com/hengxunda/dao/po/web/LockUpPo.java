package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LockUpPo {
    private String id;
    private String userId;
    private String name;
    private String phone;
    @ApiModelProperty("msc记录类型:1-AEC购买MSC；2-级差奖励MSC；3-批量拨MSC币冻结")
    private int type;
    @ApiModelProperty("释放状态：0-未完成；1-已完成")
    private int status;
    private BigDecimal amount;
    private BigDecimal restAmount;
    private Date createTime;

}
