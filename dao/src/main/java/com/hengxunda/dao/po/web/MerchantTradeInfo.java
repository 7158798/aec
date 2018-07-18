package com.hengxunda.dao.po.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Getter@Setter
public class MerchantTradeInfo {
    private int undone;
    private int total;
    public MerchantTradeInfo(){
        this.undone = 0;
        this.total = 0;
    }
}
