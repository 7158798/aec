package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.OrderLook;
import com.hengxunda.dao.entity.OrderLookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderLookMapper {
    int countByExample(OrderLookExample example);

    int deleteByExample(OrderLookExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderLook record);

    int insertSelective(OrderLook record);

    List<OrderLook> selectByExample(OrderLookExample example);

    OrderLook selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderLook record, @Param("example") OrderLookExample example);

    int updateByExample(@Param("record") OrderLook record, @Param("example") OrderLookExample example);

    int updateByPrimaryKeySelective(OrderLook record);

    int updateByPrimaryKey(OrderLook record);
}