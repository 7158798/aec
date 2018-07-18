package com.hengxunda.wapp.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderPageVo extends WAppPage{

    private List<OrderVo> list;


    public OrderPageVo(Integer page, Integer rows, Integer total, List<OrderVo> list) {
        super(page, rows, total);
        this.list = list;
    }
}
