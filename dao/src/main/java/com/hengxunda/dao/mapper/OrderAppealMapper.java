package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.OrderAppeal;
import com.hengxunda.dao.entity.OrderAppealExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderAppealMapper {
    int countByExample(OrderAppealExample example);

    int deleteByExample(OrderAppealExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderAppeal record);

    int insertSelective(OrderAppeal record);

    List<OrderAppeal> selectByExampleWithBLOBs(OrderAppealExample example);

    List<OrderAppeal> selectByExample(OrderAppealExample example);

    OrderAppeal selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderAppeal record, @Param("example") OrderAppealExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderAppeal record, @Param("example") OrderAppealExample example);

    int updateByExample(@Param("record") OrderAppeal record, @Param("example") OrderAppealExample example);

    int updateByPrimaryKeySelective(OrderAppeal record);

    int updateByPrimaryKeyWithBLOBs(OrderAppeal record);

    int updateByPrimaryKey(OrderAppeal record);
}