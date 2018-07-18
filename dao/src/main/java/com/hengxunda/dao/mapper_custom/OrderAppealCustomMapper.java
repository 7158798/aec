package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.OrderAppeal;
import com.hengxunda.dao.po.web.DealOrderAppealPo;
import com.hengxunda.dao.po.web.OrderAppealPo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderAppealCustomMapper {
    /**
     * 统计普通用户胜诉次数
     * @param userId
     * @return
     */
    int countWinAppeal(@Param("userId") String userId);

    int countOrderAppealByOrderIdAndUserId(@Param("orderId") String orderId,@Param("userId") String userId);

    /**
     * 获取申诉列表
     * @param record,page
     */
    List<OrderAppealPo> getOrdersAppeals(@Param("record")OrderAppealPo record, @Param("page")Page page);

    int countOrdersAppeals(@Param("record")OrderAppealPo record);

    List<OrderAppealPo> downloadOrdersAppeals(@Param("record") OrderAppealPo record);

    /**
     * 处理申诉订单
     * @param appealId
     */

    DealOrderAppealPo selectOrderAppealById(@Param("id") String appealId);

    /**
     * 扣币（银商）
     * @param userId
     * @param type
     * @param money
     * @return
     */
    int silverDeductCoin(@Param("userId") String userId, @Param("type") String type, @Param("money")BigDecimal money);

    OrderAppeal getOrderAppealByOrderIdAndStatus(@Param("orderId") String orderId,@Param("status") Integer status);

    OrderAppeal getOrderAppealByOrderIdAndUserIdAndStatus(@Param("orderId") String orderId,@Param("userId") String userId);

    OrderAppeal getOrderAppealByOrderId(@Param("orderId") String orderId);

}