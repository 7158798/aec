package com.hengxunda.web.service.impl;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.mapper_custom.Coin2CoinCustomMapper;
import com.hengxunda.dao.po.web.Coin2CoinAdvertPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import com.hengxunda.web.service.Coin2CoinService;
import com.hengxunda.web.vo.Coin2CoinAdvertListVo;
import com.hengxunda.web.vo.OrderListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @Author: QiuJY
 * @Date: Created in 14:42 2018/7/9
 */
@Slf4j
@Service
public class Coin2CoinServiceImpl implements Coin2CoinService {

    @Autowired
    private Coin2CoinCustomMapper coin2CoinCustomMapper;

    @Override
    public OrderListVo getCoin2CoinOrders(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,  int type, Date createTime, Page page) {
        OrderWebPo orderWebPo = getOrderWebPo(orderNo, buyPhone, buyName, sellPhone, sellName,type);
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(createTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            orderWebPo.setBeginTime(createTime).setEndTime(c.getTime());
        }

        List<OrderWebPo> orderWebPos = coin2CoinCustomMapper.getOrdersList(orderWebPo, new Page(page.getPageNo(), page.getLimit()));

        int count = coin2CoinCustomMapper.countOrdersList(orderWebPo);

        OrderListVo orderListVo = new OrderListVo();
        orderListVo.setOrderWebPos(orderWebPos).setTotal(count);
        return orderListVo;
    }

    @Override
    public List<OrderWebPo> downloadOrders(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, int type, Date beginTime, Date endTime) {
        OrderWebPo orderWebPo = getOrderWebPo(orderNo, buyPhone, buyName, sellPhone, sellName,type);
        orderWebPo.setBeginTime(beginTime).setEndTime(endTime);
        List<OrderWebPo> orderWebPos = coin2CoinCustomMapper.downloadOrders(orderWebPo);
        return orderWebPos;
    }

    @Override
    public Coin2CoinAdvertListVo getAdvert(String id, String phone, String nick, int type, Date createTime, Page page) {
        Coin2CoinAdvertPo coin2CoinAdvertPo = new Coin2CoinAdvertPo( id,  phone,  nick,  createTime,  type);

        List<Coin2CoinAdvertPo> coin2CoinAdvertPos = coin2CoinCustomMapper.getAdvertList(coin2CoinAdvertPo, new Page(page.getPageNo(), page.getLimit()));

        int count = coin2CoinCustomMapper.countCoin2CoinAdvert(coin2CoinAdvertPo);

        Coin2CoinAdvertListVo coin2CoinAdvertListVo = new Coin2CoinAdvertListVo();
        coin2CoinAdvertListVo.setCoin2CoinAdvertPos(coin2CoinAdvertPos).setTotal(count);

        return coin2CoinAdvertListVo;
    }

    @Override
    public List<Coin2CoinAdvertPo> downloadAdvert(String id, String phone, String nick, int type, Date beginTime, Date endTime) {
        Coin2CoinAdvertPo coin2CoinAdvertPo = new Coin2CoinAdvertPo( id,  phone,  nick,  null,  type);
        coin2CoinAdvertPo.setBeginTime(beginTime).setEndTime(endTime);
        List<Coin2CoinAdvertPo> advertPos = coin2CoinCustomMapper.downloadAdvert(coin2CoinAdvertPo);
        return advertPos;
    }

    private OrderWebPo getOrderWebPo(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                     int type) {
        OrderWebPo orderWebPo = new OrderWebPo();
        orderWebPo.setType(type);
        if (orderNo != null && !orderNo.equals("")) {
            orderWebPo.setOrderNo(orderNo);
        }
        if (buyName != null && !buyName.equals("")) {
            orderWebPo.setBuyName(buyName);
        }
        if (buyPhone != null && !buyPhone.equals("")) {
            orderWebPo.setBuyPhone(buyPhone);
        }
        if (sellName != null && !sellName.equals("")) {
            orderWebPo.setSellName(sellName);
        }
        if (sellPhone != null && !sellPhone.equals("")) {
            orderWebPo.setSellPhone(sellPhone);
        }
        return orderWebPo;
    }
}
