package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.WalletRecordFeePo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/13
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRecordFeeListVo extends PageVo {

    private List<WalletRecordFeePo> walletRecordFees;
}
