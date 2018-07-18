package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.OrderWebPo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/11
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderListVo extends PageVo {

    private List<OrderWebPo> orderWebPos;
}
