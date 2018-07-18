package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.YinshangQuantity;
import com.hengxunda.dao.entity.YinshangQuantityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YinshangQuantityMapper {
    int countByExample(YinshangQuantityExample example);

    int deleteByExample(YinshangQuantityExample example);

    int deleteByPrimaryKey(String advertUserId);

    int insert(YinshangQuantity record);

    int insertSelective(YinshangQuantity record);

    List<YinshangQuantity> selectByExample(YinshangQuantityExample example);

    YinshangQuantity selectByPrimaryKey(String advertUserId);

    int updateByExampleSelective(@Param("record") YinshangQuantity record, @Param("example") YinshangQuantityExample example);

    int updateByExample(@Param("record") YinshangQuantity record, @Param("example") YinshangQuantityExample example);

    int updateByPrimaryKeySelective(YinshangQuantity record);

    int updateByPrimaryKey(YinshangQuantity record);
}