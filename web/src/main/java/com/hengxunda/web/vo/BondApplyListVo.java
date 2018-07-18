package com.hengxunda.web.vo;

import com.hengxunda.dao.po.web.BondApplyPo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class BondApplyListVo extends PageVo {
    private List<BondApplyPo> bondApplyPos;
}
