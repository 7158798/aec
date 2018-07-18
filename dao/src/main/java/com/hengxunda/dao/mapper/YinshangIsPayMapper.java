package com.hengxunda.dao.mapper;

import com.hengxunda.dao.entity.YinshangIsPay;
import com.hengxunda.dao.entity.YinshangIsPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YinshangIsPayMapper {
    int countByExample(YinshangIsPayExample example);

    int deleteByExample(YinshangIsPayExample example);

    int deleteByPrimaryKey(String id);

    int insert(YinshangIsPay record);

    int insertSelective(YinshangIsPay record);

    List<YinshangIsPay> selectByExample(YinshangIsPayExample example);

    YinshangIsPay selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") YinshangIsPay record, @Param("example") YinshangIsPayExample example);

    int updateByExample(@Param("record") YinshangIsPay record, @Param("example") YinshangIsPayExample example);

    int updateByPrimaryKeySelective(YinshangIsPay record);

    int updateByPrimaryKey(YinshangIsPay record);
}