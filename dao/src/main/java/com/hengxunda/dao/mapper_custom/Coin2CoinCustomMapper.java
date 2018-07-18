package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.Coin2CoinAdvertPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @Author: QiuJY
 * @Date: Created in 15:15 2018/7/9
 */
public interface Coin2CoinCustomMapper {

    List<OrderWebPo> getOrdersList(@Param("record") OrderWebPo record, @Param("page") Page page);

    int countOrdersList(@Param("record") OrderWebPo record);

    List<OrderWebPo> downloadOrders(@Param("record")OrderWebPo record);

    List<Coin2CoinAdvertPo> getAdvertList(@Param("record")Coin2CoinAdvertPo record, @Param("page") Page page);

    int countCoin2CoinAdvert(@Param("record") Coin2CoinAdvertPo record);

    List<Coin2CoinAdvertPo> downloadAdvert(@Param("record")Coin2CoinAdvertPo record);
}
