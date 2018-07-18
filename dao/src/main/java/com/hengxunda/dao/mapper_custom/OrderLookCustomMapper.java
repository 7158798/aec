package com.hengxunda.dao.mapper_custom;

import com.hengxunda.dao.entity.OrderLook;
import org.apache.ibatis.annotations.Param;

public interface OrderLookCustomMapper {

    OrderLook getOrderLookByUserIdAndOrderId(@Param("userId") String userId,@Param("orderId") String orderId);

}