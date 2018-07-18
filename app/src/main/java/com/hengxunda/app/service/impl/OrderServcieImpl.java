package com.hengxunda.app.service.impl;

import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IOrderServcie;
import com.hengxunda.app.vo.*;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.*;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.app.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServcieImpl implements IOrderServcie {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderCustomMapper orderCustomMapper;

    @Autowired
    private AdvertMapper advertMapper;

    @Autowired
    private AdvertCustomMapper advertCustomMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private OrderAppealCustomMapper orderAppealCustomMapper;

    @Autowired
    private OrderAppealMapper orderAppealMapperr;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private YinshangQuantityCustomMapper yinshangQuantityCustomMapper;


    @Override
    public OrderPageVo getOrders(Integer status, Integer page, Integer rows) {
        int total =0;
        String userId = ShiroUtils.getShiroUserId();
        List<OrderPo> list = orderCustomMapper.getOrderList(userId,status, AppPage.startRow(page,rows),rows);
        if (list.size()>0){
            total = orderCustomMapper.countOrderList(userId,status);
        }
        return new OrderPageVo(page,rows,total, OrderVo.getInstances(list));
    }


    @Override
    public OrderDetailVo getOrderDetail(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        Advert advert = advertMapper.selectByPrimaryKey(order.getAdvertId());//查询银商广告
        //银商信息
        AdvertOfOrderPo advertOfOrderPo = advertCustomMapper.getAdvertOfOrderInfo(advert.getId());

        /*
            用户买入：展示银商支付信息
            用户卖出：展示用户支付信息
         */
        String whoPay = "";//付款者姓名
        List<BankInfoPo> bankInfoPoList = null;
        List<AlipayInfoPo> alipayInfoPoList = null;
        if(order.getType()==OrderTypeEnum.Buy.getCode()){
            //用户买入：展示银商支付信息
            bankInfoPoList = advertCustomMapper.getBankInfo(advertOfOrderPo.getId());
            alipayInfoPoList = advertCustomMapper.getAlipayInfo(advertOfOrderPo.getId());
            whoPay = ShiroUtils.getShiroUser().getName();
        }else if(order.getType()==OrderTypeEnum.Sell.getCode()){
            //用户卖出：展示用户支付信息
            bankInfoPoList = advertCustomMapper.getBankInfo(order.getUserId());
            alipayInfoPoList = advertCustomMapper.getAlipayInfo(order.getUserId());
            whoPay = advertOfOrderPo.getName();
        }

        return new OrderDetailVo(OrderVo.getInstance(order),advertOfOrderPo,new PayInfoVo(bankInfoPoList,alipayInfoPoList),whoPay);
    }

    /**
     * 我已付款操作
     * 用户买入：订单状态由未付款至已付款
     * @param id
     */
    @Override
    public void payment(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Buy.getCode() && order.getStatus() != OrderStatusEnum.unpaid.getCode(),"不支持的订单状态或类型");
        order.setStatus(OrderStatusEnum.paid.getCode()).setUpdateUser(ShiroUtils.getShiroUserId()).setUpdateTime(DateUtils.getCurentTime());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 1.用户卖出取消
     *     1.1 订单状态还是未付款
     *     1.2 订单取消状态改为发起取消
     * 2.用户买入取消
     *     2.1 订单状态由未付款/已付款改为已取消
     *     2.2 订单取消状态改为成功
     */
    @Override
    public void cancel(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        //A.check(order.getStatus() != OrderStatusEnum.unpaid.getCode(),"不支持的订单状态");
        if(order.getType()==OrderTypeEnum.Sell.getCode()){
            //1.用户卖出取消
            //   1.1 订单状态还是未付款
            //   1.2 订单取消状态改为发起取消
            order.setCancelStatus(OrderCancelStatusEnum.cancel.getCode());
            order.setUpdateTime(DateUtils.getCurentTime()).setUpdateUser(ShiroUtils.getShiroUserId());
            orderMapper.updateByPrimaryKeySelective(order);


        }else if(order.getType()==OrderTypeEnum.Buy.getCode()){
            //2.用户买入取消
            //   2.1 订单状态由未付款/已付款改为已取消
            //   2.2 订单取消状态改为成功
            order.setStatus(OrderStatusEnum.canceled.getCode()).setCancelStatus(OrderCancelStatusEnum.agree.getCode());
            order.setUpdateTime(DateUtils.getCurentTime()).setUpdateUser(ShiroUtils.getShiroUserId());
            orderMapper.updateByPrimaryKeySelective(order);
            yinshangQuantityCustomMapper.updateYinshangQuantityById(order.getAdveruUserId(), MathUtils.mul8p(new BigDecimal(-1), order.getQuantity()));
        }
    }

    /**
     * 用户买入: 申诉撤回
     *
     * 1.修改订单状态为已付款
     * 2.修改订单申诉状态为撤回
     * 3.修改t_order_appeal为撤回
     * @param id
     */
    @Override
    public void recall(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Buy.getCode() && order.getStatus() != OrderStatusEnum.appealing.getCode(),"不支持的订单状态或类型");

        OrderAppeal orderAppeal = orderAppealCustomMapper.getOrderAppealByOrderIdAndUserIdAndStatus(id,ShiroUtils.getShiroUserId());
        A.check(orderAppeal==null,"订单申诉异常");
        A.check(orderAppeal.getStatus() != AppealStatusEnum.UNTREATED.getCode(),"订单申诉正在处理，不能取消");

        order.setStatus(OrderStatusEnum.paid.getCode()).setAppealStatus(OrderAppealStatusEnum.recall.getCode());
        order.setUpdateTime(DateUtils.getCurentTime()).setUpdateUser(ShiroUtils.getShiroUserId());
        orderMapper.updateByPrimaryKeySelective(order);

        //3.修改t_order_appeal为撤回
        orderAppeal.setStatus(AppealStatusEnum.Recall.getCode()).setUpdateTime(DateUtils.getCurentTime());
        orderAppealMapperr.updateByPrimaryKeySelective(orderAppeal);
    }

    /**
     * 用户卖出：确认收款（已付款的订单才能确认收款）
     * 1.修改订单状态为已完成
     * 2.打款到银商
     *     2.1 更新用户账户
     *     2.2 更新银商账户
     *     2.3 更新钱包记录操作由冻结到c2c交易
     * @param id
     */
    @Transactional
    @Override
    public void receviceMoney(String id) {
        Date currentTime = DateUtils.getCurentTime();
        String userId = ShiroUtils.getShiroUserId();

        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Sell.getCode() && order.getStatus() != OrderStatusEnum.paid.getCode(),"不支持的订单状态或类型");
        order.setStatus(OrderStatusEnum.completed.getCode());
        order.setUpdateTime(currentTime).setUpdateUser(userId);
        orderMapper.updateByPrimaryKeySelective(order);

        /*
            2.打款到银商
               2.1 更新用户账户
               2.2 更新银商账户
               2.3 更新钱包记录操作由冻结到c2c交易
         */
        WalletRecord walletRecord = walletRecordCustomMapper.getWalletRecordBySourceAndOperate(order.getId(), WalletOperateEnum.six.getCode());

        String fromId = walletRecord.getFromId();
        BigDecimal fromAomount = walletRecord.getFromAmount();
        WalletInfo fromWalletInfo = new WalletInfo();
        fromWalletInfo.setUserId(fromId).setType(WalletTypeEnum.AEC.getCode()).setFrozenBlance(MathUtils.mul8p(fromAomount,new BigDecimal(-1)));
        fromWalletInfo.setUpdateUser(fromId).setUpdateTime(currentTime);
        //2.1 更新用户账户
        walletInfoCustomMapper.updateWalletInfoByUserIdAndType(fromWalletInfo);

        String toId = walletRecord.getToId();
        BigDecimal toAmount = walletRecord.getToAmount();
        WalletInfo toWalletInfo = new WalletInfo();
        toWalletInfo.setUserId(toId).setType(WalletTypeEnum.AEC.getCode()).setBalance(toAmount);
        toWalletInfo.setUpdateUser(toId).setUpdateTime(currentTime);
        //2.2 更新银商账户
        walletInfoCustomMapper.updateWalletInfoByUserIdAndType(toWalletInfo);

        //2.3 更新钱包记录操作由冻结到c2c交易
        walletRecord.setOperate(WalletOperateEnum.four.getCode()).setUpdateUser(userId).setUpdateTime(currentTime);
        walletRecordMapper.updateByPrimaryKeySelective(walletRecord);

    }
}
