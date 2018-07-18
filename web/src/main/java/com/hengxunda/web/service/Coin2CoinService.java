package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.Coin2CoinAdvertPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import com.hengxunda.web.vo.Coin2CoinAdvertListVo;
import com.hengxunda.web.vo.OrderListVo;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * 币币交易
 * @Author: QiuJY
 * @Date: Created in 14:41 2018/7/9
 */
public interface Coin2CoinService {

    OrderListVo getCoin2CoinOrders(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, int type, Date createTime, Page page);

    List<OrderWebPo> downloadOrders(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, int type, Date beginTime, Date endTime);

    Coin2CoinAdvertListVo getAdvert(String id, String phone, String nick, int type, Date createTime, Page page);

    List<Coin2CoinAdvertPo> downloadAdvert(String id, String phone, String nick, int type, Date beginTime, Date endTime);
}
