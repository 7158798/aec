package com.hengxunda.dao.po.web;

import com.hengxunda.dao.entity.Advert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author: lsl
 * @Date: create in 2018/6/9
 */
@Data
@Accessors(chain = true)
public class AdvertWebPo extends Advert {
    @ApiModelProperty("银商姓名")
    private String name;
    @ApiModelProperty("银商手机")
    private String phone;
    @ApiModelProperty("银商保证金")
    private BigDecimal bond;
    @ApiModelProperty("已完成数量")
    private BigDecimal completed;

    private Date beginTime;
    private Date endTime;
}
