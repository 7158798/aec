package com.hengxunda.wapp.service;

import com.hengxunda.wapp.vo.BondCanDropVo;
import com.hengxunda.wapp.vo.BondRecordVo;

import java.math.BigDecimal;
import java.util.List;

public interface IBondService {

    /**
     * 提高保证金
     *
     * @param amount
     */
    void promoteBond(BigDecimal amount);

    /**
     * 降低保证金
     *
     * @param amount
     */
    void reduceBond(BigDecimal amount);

    /**
     * 提取保证金
     */
    void extractBond();

    /**
     * 获取保证金记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<BondRecordVo> record(Integer pageNo, Integer pageSize);

    /**
     * 获取保证金可降额度
     */
    BondCanDropVo canDrop();

}
