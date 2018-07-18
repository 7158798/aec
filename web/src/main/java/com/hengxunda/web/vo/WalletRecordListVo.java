package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.WalletRecordPo;
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
public class WalletRecordListVo extends PageVo {

    private List<WalletRecordPo> walletRecordPos;
}
