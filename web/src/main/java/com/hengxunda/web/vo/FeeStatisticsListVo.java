package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.FeeStatisticsPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FeeStatisticsListVo extends PageVo{
    private List<FeeStatisticsPo> feeStatistics;
}
