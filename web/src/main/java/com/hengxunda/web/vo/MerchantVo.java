package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.MerchantPo;
import com.hengxunda.dao.po.web.MerchantTradeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantVo {
    private String id;

    private String name;

    private String nickName;

    private String userId;

    private String phone;

    private Date createTime;

    private BigDecimal bond;

    private BigDecimal balance;

    private String tradeInfo;

    private Integer status;

    public static MerchantVo format(MerchantPo m, MerchantTradeInfo info){
        MerchantVo merchantVo = new MerchantVo();
        merchantVo.setId(m.getId()).setName(m.getName()).setNickName(m.getNickName())
                .setPhone(m.getPhone()).setUserId(m.getUid()).setBond(m.getBond()).setBalance(m.getBalance())
                .setCreateTime(m.getCreateTime()).setStatus(m.getStatus())
                .setTradeInfo(info.getUndone()+"/"+info.getTotal());
        return merchantVo;
    }


}
