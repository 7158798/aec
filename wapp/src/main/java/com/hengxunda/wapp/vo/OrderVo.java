package com.hengxunda.wapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.dao.entity.Order;
import com.hengxunda.dao.po.app.OrderPo;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderVo {

    @ApiModelProperty(value = "订单id")
    private String id;
    @ApiModelProperty(value = "0.卖出,1.买入")
    private Integer type;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "0.未支付，1，已付款，2.申诉中，3.已取消，4.完成")
    private Integer status;
    @ApiModelProperty(value = "订单是否取消 0.未发起取消，1.发起取消，2.同意，3.拒绝")
    private Integer cancelStatus;
    @ApiModelProperty(value = "订单是否发起申诉：0.未发起，1.申诉中，2.申诉成功，3.申诉失败")
    private Integer appealStatus;
    @ApiModelProperty(value = "交易对")
    private String transactionPair;
    @ApiModelProperty(value = "订单使用那种法币付款,CNY,USD,EUR,HKD")
    private String legalCurrency;
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "订单是否查看，0未查看，1已查看，获取订单列表有效")
    private Integer isLook;

    @ApiModelProperty(value = "系统时间，订单详情有效")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currentTime;

    @ApiModelProperty(value = "昵称")
    private String nickName;


    @ApiModelProperty(value = "单价(订单详情有效)")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "数量(订单详情有效)")
    private BigDecimal quantity;

    @ApiModelProperty(value = "等级(订单列表有效)")
    private Integer level;

    @ApiModelProperty(value = "默认0，1.自己申诉，2.对方发起申诉,申诉订单有效")
    private Integer whoAppeal;

    public static List<OrderVo> getInstances(List<OrderPo> list){
        List<OrderVo> vos = Lists.newArrayList();
        list.forEach(order -> {
            OrderVo vo = new OrderVo();
            BeanUtils.copyProperties(order,vo);
            vo.setLegalCurrency(getLC(vo.getTransactionPair()));
            vo.setCurrentTime(DateUtils.getCurentTime());

            String orderno=order.getOrderNo();
            int length = orderno.length();
            vo.setOrderNo(order.getOrderNo().substring(length/2,orderno.length()));

            vos.add(vo);
        });
        return vos;
    }

    public static OrderVo getInstance(Order order){
        OrderVo vo = new OrderVo();
        BeanUtils.copyProperties(order,vo);
        vo.setLegalCurrency(getLC(vo.getTransactionPair()));
        vo.setCurrentTime(DateUtils.getCurentTime());

        String orderno=order.getOrderNo();
        int length = orderno.length();
        vo.setOrderNo(order.getOrderNo().substring(length/2,orderno.length()));

        if(StringUtils.isNotBlank(order.getAppealUser())){
            if (ShiroUtils.getShiroUserId().equals(order.getAppealUser())){
                vo.setWhoAppeal(1);
            } else {
                vo.setWhoAppeal(2);
            }
        }

        return vo;
    }

    private static String getLC(String transactionPair) {
        String s = "";
        if (StringUtils.isBlank(transactionPair))
            return s;
        s = transactionPair.split("/")[1];
        return s;
    }

}
