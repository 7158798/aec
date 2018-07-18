package com.hengxunda.wapp.service.impl;


import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.*;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.app.AlipayInfoPo;
import com.hengxunda.dao.po.app.BankInfoPo;
import com.hengxunda.dao.po.app.OrderPo;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.IOrderServcie;
import com.hengxunda.wapp.vo.*;
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
    private UserMapper userMapper;

    @Autowired
    private IGlobalParameterService iGlobalParameterService;

    @Autowired
    private OrderAppealCustomMapper orderAppealCustomMapper;

    @Autowired
    private OrderAppealMapper orderAppealMapperr;

    @Autowired
    private YinshangQuantityCustomMapper yinshangQuantityCustomMapper;


    @Override
    public OrderPageVo getOrders(Integer status, Integer page, Integer rows) {
        int total =0;
        String userId = ShiroUtils.getShiroUserId();
        List<OrderPo> list = orderCustomMapper.getWappOrderList(userId,status, WAppPage.startRow(page,rows),rows);
        if (list.size()>0){
            total = orderCustomMapper.countWappOrderList(userId,status);        }
        return new OrderPageVo(page,rows,total, OrderVo.getInstances(list));
    }


    @Override
    public OrderDetailVo getOrderDetail(String id) {
        String userId= ShiroUtils.getShiroUserId();
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        //买家信息
        User buyUser = userMapper.selectByPrimaryKey(order.getUserId());


         /*
            用户买入：展示银商支付信息
            用户卖出：展示用户支付信息
         */
        String whoPay = "";//付款者姓名
        List<BankInfoPo> bankInfoPoList = null;
        List<AlipayInfoPo> alipayInfoPoList = null;
        if(order.getType()==OrderTypeEnum.Buy.getCode()){
            //用户买入：展示银商支付信息
            bankInfoPoList = advertCustomMapper.getBankInfo(order.getAdveruUserId());
            alipayInfoPoList = advertCustomMapper.getAlipayInfo(order.getAdveruUserId());
            whoPay = ShiroUtils.getShiroUser().getName();
        }else if(order.getType()==OrderTypeEnum.Sell.getCode()){
            //用户卖出：展示用户支付信息
            bankInfoPoList = advertCustomMapper.getBankInfo(order.getUserId());
            alipayInfoPoList = advertCustomMapper.getAlipayInfo(order.getUserId());
            whoPay = userMapper.selectByPrimaryKey(order.getAdveruUserId()).getName();
        }
        return new OrderDetailVo(OrderVo.getInstance(order),BuyVo.getInstance(buyUser),new PayInfoVo(bankInfoPoList,alipayInfoPoList),whoPay);
    }

    /**
     * 我已付款
     * 银商买入：订单状态由未付款至已付款
     * @param id
     */
    @Override
    public void payment(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Sell.getCode() && order.getStatus() != OrderStatusEnum.unpaid.getCode(),"不支持的订单状态或类型");
        order.setStatus(OrderStatusEnum.paid.getCode()).setUpdateUser(ShiroUtils.getShiroUserId()).setUpdateTime(DateUtils.getCurentTime());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 银商买入确认取消
     *    1. 订单状态由未付款到已取消
     *    2. 订单取消状态由发起取消到成功
     *    3. 返回用户冻结的AEC
     *    4. 更新广告可用数量（加）
     * @param id
     */

    @Transactional
    @Override
    public void confirmCancel(String id) {


        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType() ==OrderTypeEnum.Sell.getCode() && order.getStatus() != OrderStatusEnum.unpaid.getCode(),"不支持的订单状态");

        Date currentTime = DateUtils.getCurentTime();

        order.setStatus(OrderStatusEnum.canceled.getCode()).setCancelStatus(OrderCancelStatusEnum.agree.getCode());
        order.setUpdateTime(currentTime).setUpdateUser(ShiroUtils.getShiroUserId());
        orderMapper.updateByPrimaryKeySelective(order);

        //3.返回用户冻结的AEC
        WalletRecord walletRecord = walletRecordCustomMapper.getWalletRecordBySourceAndOperate(order.getId(),WalletOperateEnum.six.getCode());
        walletRecord.setOperate(WalletOperateEnum.seven.getCode()).setUpdateUser(ShiroUtils.getShiroUserId()).setUpdateTime(currentTime);
        walletRecordMapper.updateByPrimaryKeySelective(walletRecord);

        BigDecimal fromAmount = walletRecord.getFromAmount();
        String fromUserId = walletRecord.getFromId();
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setUserId(fromUserId);
        walletInfo.setType(WalletTypeEnum.AEC.getCode());
        walletInfo.setFrozenBlance(MathUtils.mul8p(fromAmount,new BigDecimal(-1)));
        walletInfo.setBalance(fromAmount).setUpdateUser(fromUserId).setUpdateTime(currentTime);
        walletInfoCustomMapper.updateWalletInfoByUserIdAndType(walletInfo);


        //4 更新广告可用数量（加）
        advertCustomMapper.updateAdvertEnableStockById(order.getQuantity(), order.getAdvertId());


        yinshangQuantityCustomMapper.updateYinshangQuantityById(order.getAdveruUserId(), MathUtils.mul8p(new BigDecimal(-1), order.getQuantity()));



    }

    /**
     * 银商卖出: 申诉撤回
     * @param id
     */
    @Override
    public void recall(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Buy.getCode() && order.getStatus() != OrderStatusEnum.appealing.getCode(),"不支持的订单状态或类型");

        order.setStatus(OrderStatusEnum.paid.getCode()).setAppealStatus(OrderAppealStatusEnum.recall.getCode());
        order.setUpdateTime(DateUtils.getCurentTime()).setUpdateUser(ShiroUtils.getShiroUserId());
        orderMapper.updateByPrimaryKeySelective(order);

        OrderAppeal orderAppeal = orderAppealCustomMapper.getOrderAppealByOrderIdAndUserIdAndStatus(id,ShiroUtils.getShiroUserId());
        orderAppeal.setStatus(AppealStatusEnum.Recall.getCode()).setUpdateTime(DateUtils.getCurentTime());
        orderAppealMapperr.updateByPrimaryKeySelective(orderAppeal);
    }

    /**
     * 银商卖出：确认收款（已付款的订单才能确认收款）
     * 1.修改订单状态为已完成
     * 2.打款到用户
     *     2.1 更新用户账户
     *     2.2 更新银商账户(优先使用余额，余额不足则使用保证金)
     *     2.3 保存明细
     * @param id
     */
    @Transactional
    @Override
    public void receviceMoney(String id) {
        Date currentTime = DateUtils.getCurentTime();
        String userId = ShiroUtils.getShiroUserId();

        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Buy.getCode() && order.getStatus() != OrderStatusEnum.paid.getCode(),"不支持的订单状态或类型");
        order.setStatus(OrderStatusEnum.completed.getCode());
        order.setUpdateTime(currentTime).setUpdateUser(userId);
        orderMapper.updateByPrimaryKeySelective(order);

        //2.打款到用户
        //  2.1 更新用户账户
        //  2.2 更新银商账户(优先使用余额，余额不足则使用保证金)
        //  2.3 保存明细

        BigDecimal quantity = order.getQuantity(); //AEC数量

        //获取手续费
        BigDecimal fee = iGlobalParameterService.getFee(quantity,WithdrawFeeEnum.c2cfee.getCode());
        //用户到账AEC=用户买入AEC-手续费
        BigDecimal toAmount = MathUtils.sub8p(quantity, fee);

        WalletInfo userWallet = new WalletInfo();
        userWallet.setUserId(order.getUserId()).setType(WalletTypeEnum.AEC.getCode()).setBalance(toAmount);
        userWallet.setUpdateUser(order.getUserId()).setUpdateTime(currentTime);
        //2.1 更新用户账户
        walletInfoCustomMapper.updateWalletInfoByUserIdAndType(userWallet);

        //2.2 更新银商账户(优先使用余额，余额不足则使用保证金)
        WalletInfo yinShangWallet = walletInfoCustomMapper.getByUserIdAndTypeForUpdate(order.getAdveruUserId(),WalletTypeEnum.AEC.getCode()); //查询银商钱包信息
        if (MathUtils.greatOrEqualForBg(yinShangWallet.getBalance(),quantity)){
            //银商余额充足
            WalletInfo ysWallet = new WalletInfo();
            ysWallet.setUserId(userId).setType(WalletTypeEnum.AEC.getCode()).setBalance(MathUtils.mul8p(quantity,new BigDecimal(-1)));
            ysWallet.setUpdateUser(userId).setUpdateTime(currentTime);
            walletInfoCustomMapper.updateWalletInfoByUserIdAndType(ysWallet);

        }else{
            //使用保证金
            WalletInfo ysWallet = new WalletInfo();
            ysWallet.setUserId(userId).setType(WalletTypeEnum.AEC.getCode()).setBond(MathUtils.mul8p(quantity,new BigDecimal(-1)));
            ysWallet.setUpdateUser(userId).setUpdateTime(currentTime);
            walletInfoCustomMapper.updateWalletInfoByUserIdAndType(ysWallet);
        }

        //查询用户钱包信息
        WalletInfo userWalletInfo = walletInfoCustomMapper.getByUserIdAndType(order.getUserId(),WalletTypeEnum.AEC.getCode());

        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID()).setTransactionPair(TransPairEnum.AEC2AEC.getCode());
        walletRecord.setFromId(userId).setToId(order.getUserId());
        walletRecord.setFromAddress(yinShangWallet.getAddress()).setToAddress(userWalletInfo.getAddress());
        walletRecord.setFromAmount(quantity);
        walletRecord.setToAmount(toAmount);
        walletRecord.setFee(fee);

        walletRecord.setOperate(WalletOperateEnum.four.getCode()).setDescri("c2c交易-银商卖出");
        walletRecord.setSource(order.getId());
        walletRecord.setCreateTime(currentTime);
        walletRecordMapper.insertSelective(walletRecord);

        yinshangQuantityCustomMapper.updateYinshangQuantityById(order.getAdveruUserId(), MathUtils.mul8p(new BigDecimal(-1), quantity));

    }


    @Override
    public void reject(String id) {
        Order order = orderMapper.selectByPrimaryKey(id);//查询订单
        A.check(order==null,"当前订单不存在");
        A.check(order.getType()==OrderTypeEnum.Sell.getCode() && order.getStatus() != OrderStatusEnum.unpaid.getCode(),"不支持的订单状态或类型");
        order.setCancelStatus(OrderCancelStatusEnum.refuse.getCode()).setUpdateUser(ShiroUtils.getShiroUserId()).setUpdateTime(DateUtils.getCurentTime());
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
