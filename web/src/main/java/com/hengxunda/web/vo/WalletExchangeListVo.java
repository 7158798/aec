package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.WalletExchangePo;
import com.hengxunda.dao.po.web.WalletLog;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletExchangeListVo extends PageVo{

    private List<WalletExchangePo> walletExchangePos;
}
