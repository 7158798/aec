package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.po.web.MscProfitsPo;

import java.util.List;

public interface MscProfitsCustomMapper {
    /**
     * 查询股权分红记录
     *
     * @return
     */
    List<MscProfitsPo> queryAllMscProfits();

}