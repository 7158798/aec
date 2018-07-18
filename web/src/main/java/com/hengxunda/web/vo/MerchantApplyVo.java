package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.MerchantApplyPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/8
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantApplyVo extends PageVo {
    private  List<MerchantApplyPo> merchantApplyPos;
}
