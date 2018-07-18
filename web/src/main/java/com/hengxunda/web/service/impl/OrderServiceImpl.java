package com.hengxunda.web.service.impl;

import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.Order;
import com.hengxunda.dao.entity.OrderAppeal;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletRecord;
import com.hengxunda.dao.mapper.OrderAppealMapper;
import com.hengxunda.dao.mapper.OrderMapper;
import com.hengxunda.dao.mapper.WalletRecordMapper;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.web.DealOrderAppealPo;
import com.hengxunda.dao.po.web.OrderAppealPo;
import com.hengxunda.dao.po.web.OrderWebPo;
import com.hengxunda.web.dto.TradeDto;
import com.hengxunda.web.service.IOrderService;
import com.hengxunda.web.utils.MessageUtil;
import com.hengxunda.web.vo.OrderAppealListVo;
import com.hengxunda.web.vo.OrderListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: lsl
 * @Date: create in 2018/6/11
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderCustomMapper orderCustomMapper;
    @Autowired
    private OrderAppealCustomMapper appealCustomMapper;

    @Autowired
    private OrderAppealMapper orderAppealMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordCustomMapper walletRecordCustomMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private GlobalParameterCustomMapper globalParameterCustomMapper;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private AdvertCustomMapper advertCustomMapper;

    @Autowired
    private  YinshangQuantityCustomMapper yinshangQuantityCustomMapper;

    /**
     * 申诉相关
     */

    @Override
    public OrderAppealListVo getOrderAppeals(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, int role, int type, int status, Page page) {

        OrderAppealPo orderAppealPo = getOrderAppealPo(orderNo, buyPhone, buyName, sellPhone, sellName, role, type, status);
        List<OrderAppealPo> orderAppealPos = appealCustomMapper.getOrdersAppeals(orderAppealPo, new Page(page.getPageNo(), page.getLimit()));
        int count = appealCustomMapper.countOrdersAppeals(orderAppealPo);
        OrderAppealListVo orderAppealListVo = new OrderAppealListVo();
        orderAppealListVo.setOrderAppealPos(orderAppealPos).setTotal(count);
        return orderAppealListVo;
    }

    @Override
    public List<OrderAppealPo> downloadOrderAppeals(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, int role, int type, int status, Date beginTime, Date endTimne) {

        OrderAppealPo orderAppealPo = getOrderAppealPo(orderNo, buyPhone, buyName, sellPhone, sellName, role, type, status);
        List<OrderAppealPo> orderAppealPos = appealCustomMapper.downloadOrdersAppeals(orderAppealPo);
        return orderAppealPos;
    }

    /**
     * 1、判断订单是买入还是卖出
     * 2、判断订单用户ID与申诉用户ID是否相同，如果相同则说明此申诉为用户发起，反之为银商发起
     * 3、当申诉为用户发起且为买入时，如果判定买家胜出，则从银商用户账户余额或者保证金中扣币（扣除手续费），消费记录，并将订单状态修改为订单申诉成功，申诉状态修改为买家胜出，如果判断卖家胜出，则将订单状态修改为订单申诉失败，申诉状态修改为卖家胜出
     * 4、当申诉为用户发起且为卖出时，如果判定卖家胜出，则将订单状态修改为订单申诉成功，申诉状态修改为卖家胜出，如果判断买家胜出，则从用户冻结余额中扣币，消费记录，并将订单状态修改为订单申诉失败，申诉状态修改为买家胜出，
     * 5、当申诉为银商发起且为买入时，如果判定买家胜出，则从用户冻结余额中扣币（扣除手续费），消费记录，并将订单状态修改为订单申诉成功，申诉状态修改为买家胜出，如果判断卖家胜出，则将订单状态修改为订单申诉失败，申诉状态修改为卖家胜出
     * 6、当申诉为银商发起且为卖出时，如果判定卖家胜出，则将订单状态修改为订单申诉成功，申诉状态修改为卖家胜出，如果判断买家胜出，则从从银商用户账户余额或者保证金中扣币（扣除手续费），消费记录，并将订单状态修改为订单申诉失败，申诉状态修改为买家胜出，
     *
     * @param appealId
     * @param status
     */
    @Override
    @Transactional
    public void reviewAppealOrder(String appealId, Integer status) {
        DealOrderAppealPo dealOrderAppealPo = appealCustomMapper.selectOrderAppealById(appealId);
        A.check(Objects.isNull(dealOrderAppealPo), "申诉订单不存在！");

        //修改申诉订单状态
        updateOrderAppealStatus(appealId, status);
        //卖出
        if (OrderTypeEnum.Sell.getCode() == dealOrderAppealPo.getType()) {
            //用户发起
            if (dealOrderAppealPo.getUserId().equals(dealOrderAppealPo.getAccuseUserId())) {
                WalletRecord walletRecord = walletRecordCustomMapper.getWalletRecordBySource(dealOrderAppealPo.getOrderId());
                A.check(Objects.isNull(walletRecord), "钱包交易记录不存在！");
                //卖家胜出
                if (AppealStatusEnum.SELL_WIN.getCode() == status) {

                    //订单状态未支付（0）、申诉成功（2）
                    updateOrderStatus(dealOrderAppealPo.getOrderId(), OrderStatusEnum.unpaid.getCode(), OrderAppealStatusEnum.win.getCode());

                    orderAppealWinMessage(dealOrderAppealPo.getAccuseUserId(), dealOrderAppealPo);
                    orderAppealLoseMessage(dealOrderAppealPo.getAccusedUserId(), dealOrderAppealPo);

                } else {

                    //扣币(用户)
                    walletRecord.setOperate(WalletOperateEnum.four.getCode())
                            .setDescri(WalletOperateEnum.four.getValue());
                    walletRecordMapper.updateByPrimaryKeySelective(walletRecord);

                    int count = walletInfoCustomMapper.updateAppealOrderBalance(walletRecord);
                    A.check(count != 2, "申诉处理异常，用户余额可能不足！");
                    //订单状态已完成（4）、申诉失败（3）
                    updateOrderStatus(dealOrderAppealPo.getOrderId(), OrderStatusEnum.completed.getCode(), OrderAppealStatusEnum.lose.getCode());
                    orderAppealWinMessage(dealOrderAppealPo.getAccusedUserId(), dealOrderAppealPo);
                    orderAppealLoseMessage(dealOrderAppealPo.getAccuseUserId(), dealOrderAppealPo);
                }
            }

        } else {
            //用户发起
            if (dealOrderAppealPo.getUserId().equals(dealOrderAppealPo.getAccuseUserId())) {
                //卖家胜出
                if (AppealStatusEnum.SELL_WIN.getCode() == status) {
                    //订单状态未支付（0）、申诉失败（3）
                    updateOrderStatus(dealOrderAppealPo.getOrderId(), OrderStatusEnum.unpaid.getCode(), OrderAppealStatusEnum.lose.getCode());
                    orderAppealWinMessage(dealOrderAppealPo.getAccusedUserId(), dealOrderAppealPo);
                    orderAppealLoseMessage(dealOrderAppealPo.getAccuseUserId(), dealOrderAppealPo);

                } else {
                    //扣币（银商）
                    TradeDto tradeDto = convertTradeDto(dealOrderAppealPo);
                    tradeDto.setFromId(dealOrderAppealPo.getAccusedUserId()).setToId(dealOrderAppealPo.getAccuseUserId());
                    transfer(tradeDto);
                    //订单状态已完成（4）、申诉成功（2）
                    updateOrderStatus(dealOrderAppealPo.getOrderId(), OrderStatusEnum.completed.getCode(), OrderAppealStatusEnum.win.getCode());
                    orderAppealWinMessage(dealOrderAppealPo.getAccuseUserId(), dealOrderAppealPo);
                    orderAppealLoseMessage(dealOrderAppealPo.getAccusedUserId(), dealOrderAppealPo);

                    yinshangQuantityCustomMapper.updateYinshangQuantityById(dealOrderAppealPo.getAccusedUserId(), MathUtils.mul8p(new BigDecimal(-1), dealOrderAppealPo.getAmount()));

                }
            } else {
                //卖家胜出
                if (AppealStatusEnum.SELL_WIN.getCode() == status) {
                    //订单状态未支付（0）、申诉成功（2）
                    updateOrderStatus(dealOrderAppealPo.getOrderId(), OrderStatusEnum.unpaid.getCode(), OrderAppealStatusEnum.win.getCode());
                    orderAppealWinMessage(dealOrderAppealPo.getAccuseUserId(), dealOrderAppealPo);
                    orderAppealLoseMessage(dealOrderAppealPo.getAccusedUserId(), dealOrderAppealPo);
                } else {
                    //扣币（银商）
                    TradeDto tradeDto = convertTradeDto(dealOrderAppealPo);
                    tradeDto.setFromId(dealOrderAppealPo.getAccuseUserId()).setToId(dealOrderAppealPo.getAccusedUserId());
                    transfer(tradeDto);

                    //订单状态已完成（4）、申诉失败（3）
                    updateOrderStatus(dealOrderAppealPo.getOrderId(), OrderStatusEnum.completed.getCode(), OrderAppealStatusEnum.lose.getCode());
                    orderAppealWinMessage(dealOrderAppealPo.getAccusedUserId(), dealOrderAppealPo);
                    orderAppealLoseMessage(dealOrderAppealPo.getAccuseUserId(), dealOrderAppealPo);

                    yinshangQuantityCustomMapper.updateYinshangQuantityById(dealOrderAppealPo.getAccuseUserId(), MathUtils.mul8p(new BigDecimal(-1), dealOrderAppealPo.getAmount()));
                }
            }
        }

    }

    /**
     * 申诉失败消息
     * @param userId
     * @param dealOrderAppealPo
     */
    public void orderAppealLoseMessage(String userId, DealOrderAppealPo dealOrderAppealPo) {
            messageUtil.messageWrite(userId, NoticeTypeEnum.notice.getCode(), "您有一笔订单申诉失败！", null, "您申诉订单[" + dealOrderAppealPo.getOrderNo() + "][申诉原因："+dealOrderAppealPo.getDescri()+"]申诉失败!");
    }

    /**
     * 申诉成功消息
     * @param userId
     * @param dealOrderAppealPo
     */
    public void orderAppealWinMessage(String userId, DealOrderAppealPo dealOrderAppealPo) {
        messageUtil.messageWrite(userId, NoticeTypeEnum.notice.getCode(), "您有一笔订单申诉成功！", null, "您申诉订单[" + dealOrderAppealPo.getOrderNo() + "][申诉原因："+dealOrderAppealPo.getDescri()+"]申诉成功");
    }

    /**
     * 对象转换
     *
     * @param dealOrderAppealPo
     * @return
     */
    public TradeDto convertTradeDto(DealOrderAppealPo dealOrderAppealPo) {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAmount(dealOrderAppealPo.getAmount())
                .setOrderId(dealOrderAppealPo.getOrderId());
        return tradeDto;
    }


    /**
     * 修改申诉状态
     *
     * @param appealId
     * @param status
     */
    public void updateOrderAppealStatus(String appealId, Integer status) {
        OrderAppeal orderAppeal = new OrderAppeal();
        orderAppeal.setId(appealId).setStatus(status);
        orderAppealMapper.updateByPrimaryKeySelective(orderAppeal);
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param status
     * @param appealStatus
     */
    public void updateOrderStatus(String orderId, Integer status, Integer appealStatus) {
        Order order = new Order();
        order.setId(orderId).setStatus(status).setAppealStatus(appealStatus);
        orderMapper.updateByPrimaryKeySelective(order);
    }


    /**
     * 交易
     *
     * @param tradeDto
     */
    public void transfer(TradeDto tradeDto) {

        String coinType = WalletTypeEnum.AEC.getCode();

        WalletInfo fromWalletInfo = walletInfoCustomMapper.getByUserIdAndType(tradeDto.getFromId(), coinType);
        WalletInfo toWalletInfo = walletInfoCustomMapper.getByUserIdAndType(tradeDto.getToId(), coinType);

        A.check(Objects.isNull(fromWalletInfo), "钱包地址不存在！");
        A.check(Objects.isNull(toWalletInfo), "钱包地址不存在！");

        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID())
                .setFromId(tradeDto.getFromId())
                .setToId(tradeDto.getToId())
                .setFromAddress(fromWalletInfo.getAddress())
                .setToAddress(toWalletInfo.getAddress())
                .setFromAmount(tradeDto.getAmount())
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setSource(tradeDto.getOrderId())
                .setCreateTime(DateUtils.getCurentTime())
                .setOperate(WalletOperateEnum.four.getCode())
                .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                .setDescri(WalletOperateEnum.four.getValue());
        BigDecimal fee = calculatingCharge(tradeDto.getAmount(), GlobalParameterEnum.C2CFeeRule.getCode(), GlobalParameterEnum.C2CFeeAmount.getCode(), GlobalParameterEnum.C2CFeePercent.getCode());
        walletRecord.setToAmount(MathUtils.sub8p(tradeDto.getAmount(), fee));
        walletRecord.setFee(fee);

        A.check(MathUtils.lessForBg(MathUtils.add8p(fromWalletInfo.getBalance(), fromWalletInfo.getBond()), tradeDto.getAmount()), "余额不足！");
        int count = walletInfoCustomMapper.updateAppealOrderSilverBalance(walletRecord);
        A.check(count != 1, "申诉处理异常，用户余额可能不足！");
        //新增钱包记录
        walletRecordMapper.insertSelective(walletRecord);
    }

    /**
     * 计算手续费
     *
     * @param amount
     * @param feeRule
     * @param feeAmount
     * @param feePercent
     * @return
     */
    private BigDecimal calculatingCharge(BigDecimal amount, String feeRule, String feeAmount, String feePercent) {
        switch (getGlobalParameter(feeRule)) {
            case "1":
                return amount(amount, feeAmount);
            case "2":
                return percentage(amount, feePercent);
            case "3":
                return hybrid(amount, feePercent, feeAmount);
        }

        return null;
    }

    private String getGlobalParameter(String code) {

        return globalParameterCustomMapper.selectByCronKey(code).getCronValue();
    }

    /**
     * 按金额计算
     *
     * @param amount
     * @param code
     * @return
     */
    private BigDecimal amount(BigDecimal amount, String code) {

        return MathUtils.toBigDecimal(getGlobalParameter(code));
    }

    /**
     * 按百分比计算
     *
     * @param amount
     * @param code
     * @return
     */
    private BigDecimal percentage(BigDecimal amount, String code) {

        return MathUtils.mul8p(amount, MathUtils.toBigDecimal(getGlobalParameter(code)));
    }

    /**
     * 按金额 + 百分比计算
     *
     * @param amount
     * @param args
     * @return
     */
    private BigDecimal hybrid(BigDecimal amount, String... args) {

        return MathUtils.add8p(percentage(amount, args[0]), toBigDecimal(args[1]));
    }

    private BigDecimal toBigDecimal(String code) {

        return MathUtils.toBigDecimal(getGlobalParameter(code));
    }

    private OrderAppealPo getOrderAppealPo(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                           int role, int type, int status) {
        OrderAppealPo orderAppealPo = new OrderAppealPo();
        orderAppealPo.setType(type).setStatus(status).setRole(role);
        if (orderNo != null && !orderNo.equals("")) {
            orderAppealPo.setOrderNo(orderNo);
        }
        if (buyName != null && !buyName.equals("")) {
            orderAppealPo.setBuyName(buyName);
        }
        if (buyPhone != null && !buyPhone.equals("")) {
            orderAppealPo.setBuyPhone(buyPhone);
        }
        if (sellName != null && !sellName.equals("")) {
            orderAppealPo.setSellName(sellName);
        }
        if (sellPhone != null && !sellPhone.equals("")) {
            orderAppealPo.setSellPhone(sellPhone);
        }

        return orderAppealPo;
    }

    /**
     * 订单查看相关
     */

    @Override
    public OrderListVo getOrdersForWeb(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, BigDecimal number, BigDecimal price, BigDecimal amount, int type, int status, Date createTime, Page page) {
        OrderWebPo orderWebPo = getOrderWebPo(orderNo, buyPhone, buyName, sellPhone, sellName, number, price, amount, type, status);
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(createTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            orderWebPo.setBeginTime(createTime).setEndTime(c.getTime());
        }
        List<OrderWebPo> orderWebPos = orderCustomMapper.getOrdersForWeb(orderWebPo, new Page(page.getPageNo(), page.getLimit()));
        int count = orderCustomMapper.countOrdersForWeb(orderWebPo);
        OrderListVo orderListVo = new OrderListVo();
        orderListVo.setOrderWebPos(orderWebPos).setTotal(count);
        return orderListVo;
    }

    @Override
    public List<OrderWebPo> downloadOrdersForWeb(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName, BigDecimal number, BigDecimal price, BigDecimal amount, int type, int status, Date beginTime, Date endTimne) {
        OrderWebPo orderWebPo = getOrderWebPo(orderNo, buyPhone, buyName, sellPhone, sellName, number, price, amount, type, status);
        orderWebPo.setBeginTime(beginTime).setEndTime(endTimne);
        List<OrderWebPo> orderWebPos = orderCustomMapper.downloadOrdersForWeb(orderWebPo);
        return orderWebPos;
    }


    private OrderWebPo getOrderWebPo(String orderNo, String buyPhone, String buyName, String sellPhone, String sellName,
                                     BigDecimal number, BigDecimal price, BigDecimal amount, int type, int status) {
        OrderWebPo orderWebPo = new OrderWebPo();
        orderWebPo.setType(type).setStatus(status);
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
        if (number != null) {
            orderWebPo.setNumber(number);
        }
        if (price != null) {
            orderWebPo.setPrice(price);
        }
        if (amount != null) {
            orderWebPo.setAmount(amount);
        }

        return orderWebPo;
    }
}
