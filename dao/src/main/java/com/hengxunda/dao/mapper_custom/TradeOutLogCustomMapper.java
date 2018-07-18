package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.TradeOutLog;
import org.apache.ibatis.annotations.Param;

public interface TradeOutLogCustomMapper {

    TradeOutLog selectByBillCode(String id);

    int updateByBillCode(@Param("billCode") String billCode, @Param("status") String status);

}