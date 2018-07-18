package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.OrderAppealPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/11
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderAppealListVo extends PageVo {
    private List<OrderAppealPo> orderAppealPos;
}
