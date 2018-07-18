package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TimePo {
    @ApiModelProperty("开始时间")
    private Date beginTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
}
