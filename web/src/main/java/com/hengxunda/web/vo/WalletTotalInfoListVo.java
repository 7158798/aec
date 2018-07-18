package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.WalletTotalInfoPo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/15
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WalletTotalInfoListVo extends PageVo {


    private List<WalletTotalInfoPo> walletTotalInfoPos;


}
