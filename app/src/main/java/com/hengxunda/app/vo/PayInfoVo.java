package com.hengxunda.app.vo;

import com.google.common.collect.Lists;
import com.hengxunda.dao.po.app.AlipayInfoPo;
import com.hengxunda.dao.po.app.BankInfoPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayInfoVo {
    @ApiModelProperty(value = "卡支付信息")
    private List<BankInfoPo> bankInfoPos;
    @ApiModelProperty(value = "支付宝信息")
    private List<AlipayInfoPo> alipayInfoPos;

    public PayInfoVo(List<BankInfoPo> bankInfoPos, List<AlipayInfoPo> alipayInfoPos) {
        if (bankInfoPos== null || bankInfoPos.get(0)==null){
            bankInfoPos = Lists.newArrayList();
        }

        if (alipayInfoPos==null || alipayInfoPos.get(0)==null){
            alipayInfoPos = Lists.newArrayList();
        }

        this.bankInfoPos = bankInfoPos;
        this.alipayInfoPos = alipayInfoPos;
    }
}
