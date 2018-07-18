package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.Order;
import com.hengxunda.dao.po.app.OrderPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderCustomMapper {

    int count(Order order);

    BigDecimal getCountQuantityByTypeOrStatus(@Param("type") Integer type,
                                              @Param("userId") String userId,
                                              @Param("list") List<Integer> list);

    List<Order> findList(Order order);

    //查询订单列表
    List<OrderPo> getOrderList(@Param("userId") String userId, @Param("status") Integer status, @Param("startRow") Integer startRow, @Param("rows") Integer rows);
    int countOrderList(@Param("userId") String userId, @Param("status") Integer status);


    List<OrderPo> getWappOrderList(@Param("userId") String userId, @Param("status") Integer status, @Param("startRow") Integer startRow, @Param("rows") Integer rows);
    int countWappOrderList(@Param("userId") String userId, @Param("status") Integer status);

    List<OrderWebPo> getOrdersForWeb(@Param("record") OrderWebPo record, @Param("page")Page page);

    int countOrdersForWeb(@Param("record")OrderWebPo record);

    List<OrderWebPo> downloadOrdersForWeb(@Param("record") OrderWebPo record );

    Integer getTotalQuantityYinShang(@Param("userId") String userId);

    Integer countUnpaid(@Param("userId") String userId,@Param("type") Integer type);
    Integer countUnpaidByUserId(@Param("userId") String userId);
}