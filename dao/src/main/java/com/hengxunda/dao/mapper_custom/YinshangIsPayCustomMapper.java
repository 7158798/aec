package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.YinshangIsPay;
import org.apache.ibatis.annotations.Param;

public interface YinshangIsPayCustomMapper {
    YinshangIsPay selectByUserId(@Param("userId") String userId);
}