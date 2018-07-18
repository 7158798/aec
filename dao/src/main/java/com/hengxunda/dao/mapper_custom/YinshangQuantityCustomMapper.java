package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.YinshangQuantity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface YinshangQuantityCustomMapper {

    int updateYinshangQuantityById(@Param("advertUserId") String advertUserId,@Param("quantity") BigDecimal quantity);

    YinshangQuantity getYinshangQuantityById(@Param("advertUserId") String advertUserId);

    int insertYinshangQuantity(@Param("advertUserId") String advertUserId,@Param("quantity") BigDecimal quantity);

}