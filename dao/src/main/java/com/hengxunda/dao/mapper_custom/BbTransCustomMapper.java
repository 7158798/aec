package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.po.app.BbTransPo;

import java.math.BigDecimal;
import java.util.List;

public interface BbTransCustomMapper {

    /**
     * 查询币币交易记录
     * @param userId
     * @return
     */
    List<BbTransPo> queryBbTransList(String userId);

    /**
     * 查询最近成交价
     * @return
     */
    BigDecimal queryLastTransPrice();

}