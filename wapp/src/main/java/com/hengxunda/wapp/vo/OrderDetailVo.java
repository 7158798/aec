package com.hengxunda.wapp.vo;

import com.hengxunda.dao.po.app.AdvertOfOrderPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetailVo {

    @ApiModelProperty(value = "订单信息")
    private OrderVo orderVo;

    @ApiModelProperty(value = "买家信息")
    private BuyVo buyVo;

    @ApiModelProperty(value = "支付信息")
    private PayInfoVo payInfoVo;


    @ApiModelProperty("付款者姓名")
    private String whoPay;


    public OrderDetailVo(OrderVo orderVo, BuyVo buyVo, PayInfoVo payInfoVo,String whoPay) {
        this.orderVo = orderVo;
        this.buyVo = buyVo;
        this.payInfoVo = payInfoVo;
        this.whoPay=whoPay;
    }
}
