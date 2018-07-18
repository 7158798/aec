package com.hengxunda.dao.po.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 * 币币广告
 * @Author: QiuJY
 * @Date: Created in 17:44 2018/7/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Coin2CoinAdvertPo {

    private String id;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("数量")
    private BigDecimal number;
    @ApiModelProperty("已完成")
    private BigDecimal finishTotal;
    @ApiModelProperty("最低额")
    private Integer min = 1;
    @ApiModelProperty("单价")
    private BigDecimal price;
    @ApiModelProperty("总金额")
    private BigDecimal amount;
    @ApiModelProperty("下单时间")
    private Date createTime;
    @ApiModelProperty("订单类型0.卖出,1.买入")
    private int type;
    @ApiModelProperty("币种交易类型")
    private String tradeType = "MSC/AEC";
    @ApiModelProperty("系统内部使用，前端开发不用考虑")
    private Date beginTime;
    @ApiModelProperty("系统内部使用，前端开发不用考虑")
    private Date endTime;

    public Coin2CoinAdvertPo(String id, String phone, String nickName, Date createTime, int type) {
        this.id = id;
        this.phone = phone;
        this.nickName = nickName;
        this.type = type;
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(createTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            this.beginTime = createTime;
            this.endTime = c.getTime();
        }
    }
}
