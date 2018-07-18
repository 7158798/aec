package com.hengxunda.app.service.impl;

import com.google.common.collect.Lists;
import com.hengxunda.app.dto.AdvertBuyAndSellDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.ITransactionService;
import com.hengxunda.app.vo.AdvertPageVo;
import com.hengxunda.app.vo.AppPage;
import com.hengxunda.app.vo.TransactionPairVo;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.exception.ServiceException;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.*;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.app.AdvertPo;
import com.hengxunda.generalservice.common.SerialCodeGenerator;
import com.hengxunda.generalservice.redission.RedissLockUtil;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.ITaxRateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements ITransactionService {

    private static final String key = "app_advert_buy";
    public static final int waitTime = 2;
    public static final int leaseTime = 600;

    public static final int expire = 600000;

//    @Autowired
//    private IRedisTemplateSerivice iRedisTemplateSerivice;

    @Autowired
    private IGlobalParameterService iGlobalParameterService;

    @Autowired
    private AdvertCustomMapper advertCustomMapper;

    @Autowired
    private AdvertMapper advertMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SerialCodeGenerator serialCodeGenerator;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ITaxRateService iTaxRateService;

    @Autowired
    private OrderCustomMapper orderCustomMapper;

    @Autowired
    private YinshangIsPayCustomMapper yinshangIsPayCustomMapper;

    @Autowired
    private YinshangQuantityMapper yinshangQuantityMapper;

    @Autowired
    private YinshangQuantityCustomMapper yinshangQuantityCustomMapper;

    @Override
    public List<TransactionPairVo> getTransactionPairs() {
        List<TransactionPairVo> list = new ArrayList<>();
        GlobalParameter globalParameter = iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.AEC2CNY.getCode());
        if (globalParameter==null)
            return list;
        return getTransactionPairs(list,globalParameter);
    }

    private List<TransactionPairVo> getTransactionPairs(List<TransactionPairVo> list,GlobalParameter globalParameter){

        String value = globalParameter.getCronValue();
        for (TransPairLegalEnum t : TransPairLegalEnum.values()){
            TransactionPairVo vo = new TransactionPairVo();

            if(TransPairLegalEnum.AEC2CNY.getCode().equals(t.getCode())){
                vo.setTransactionPair(TransPairLegalEnum.AEC2CNY.getCode()).setQuotation(value).setIsUse(0);
                list.add(vo);
            }else if(TransPairLegalEnum.AEC2USD.getCode().equals(t.getCode())){
                vo.setTransactionPair(TransPairLegalEnum.AEC2USD.getCode());
                String money=iTaxRateService.cny2usd();
                if (StringUtils.isBlank(money)){
                    vo.setQuotation("").setIsUse(1);
                }else{
                    vo.setQuotation(MathUtils.mul8p(new BigDecimal(value),new BigDecimal(money)).toString()).setIsUse(0);
                }
                list.add(vo);
            }else if(TransPairLegalEnum.AEC2HKD.getCode().equals(t.getCode())){
                vo.setTransactionPair(TransPairLegalEnum.AEC2HKD.getCode());
                String money=iTaxRateService.cny2hkd();
                if (StringUtils.isBlank(money)){
                    vo.setQuotation("").setIsUse(1);
                }else{
                    vo.setQuotation(MathUtils.mul8p(new BigDecimal(value),new BigDecimal(money)).toString()).setIsUse(0);
                }
                list.add(vo);
            } else if (TransPairLegalEnum.AEC2EUR.getCode().equals(t.getCode())) {
                vo.setTransactionPair(TransPairLegalEnum.AEC2EUR.getCode());
                String money=iTaxRateService.cny2eur();
                if (StringUtils.isBlank(money)){
                    vo.setQuotation("").setIsUse(1);
                }else{
                    vo.setQuotation(MathUtils.mul8p(new BigDecimal(value),new BigDecimal(money)).toString()).setIsUse(0);
                }
                list.add(vo);

            }

        }

        return list;
    }



    @Override
    public AdvertPageVo getAdverts(Integer type, String transactionPair, Integer page, Integer rows) {
        int total = 0;
        List<AdvertPo> advertPos = Lists.newArrayList();

        List<AdvertPo> list = advertCustomMapper.getAdverList1(type,transactionPair, AppPage.startRow(page,rows),rows);

        int removeTotal = 0;

        Date time = getTime();

        //买入
        if (type==OrderTypeEnum.Buy.getCode()){
            list.forEach(n->{

                if (TransPairLegalEnum.AEC2USD.getCode().equals(transactionPair)){
                    n.setMaxV(MathUtils.mul8p(n.getMaxV(),new BigDecimal(iTaxRateService.cny2usd())));
                    n.setMinV(MathUtils.mul8p(n.getMinV(),new BigDecimal(iTaxRateService.cny2usd())));
                    n.setUnitPrice(MathUtils.mul8p(n.getUnitPrice(),new BigDecimal(iTaxRateService.cny2usd())));
                }

                if (TransPairLegalEnum.AEC2EUR.getCode().equals(transactionPair)){
                    n.setMaxV(MathUtils.mul8p(n.getMaxV(),new BigDecimal(iTaxRateService.cny2eur())));
                    n.setMinV(MathUtils.mul8p(n.getMinV(),new BigDecimal(iTaxRateService.cny2eur())));
                    n.setUnitPrice(MathUtils.mul8p(n.getUnitPrice(),new BigDecimal(iTaxRateService.cny2eur())));
                }

                if (TransPairLegalEnum.AEC2HKD.getCode().equals(transactionPair)){
                    n.setMaxV(MathUtils.mul8p(n.getMaxV(),new BigDecimal(iTaxRateService.cny2hkd())));
                    n.setMinV(MathUtils.mul8p(n.getMinV(),new BigDecimal(iTaxRateService.cny2hkd())));
                    n.setUnitPrice(MathUtils.mul8p(n.getUnitPrice(),new BigDecimal(iTaxRateService.cny2hkd())));

                }

                if (n.getCNY()==0 || n.getUSD()==0 || n.getHKD()==0 ||n.getEUR()==0) {
                    n.setIsBank(0);
                } else {
                    n.setIsBank(1);
                }

                //更新广告数量（与银商保证金余额，银商余额有关）
                WalletInfo yinshangWalletInfo = walletInfoCustomMapper.getByUserIdAndType(n.getAdvertUserId(), WalletTypeEnum.AEC.getCode());
                BigDecimal bondBalance = getbondBalance(n.getAdvertUserId(),yinshangWalletInfo);  //获取保证金余额

                if (MathUtils.greatOrEqualForBg(yinshangWalletInfo.getBalance(),bondBalance)){
                    n.setEnableStock(bondBalance);
                }else{
                    n.setEnableStock(yinshangWalletInfo.getBalance());
                }

                //查询近30天完成的订单
                Integer completedOrder = advertCustomMapper.getCompetedOrder(n.getAdvertUserId(),time);
                if (completedOrder==null)
                    completedOrder = 0;
                n.setCompletedOrder(completedOrder);


            });

            //数量为0的广告不显示
            for (AdvertPo advertPo:list) {
                if (advertPo.getEnableStock().intValue()!=0){
                    advertPos.add(advertPo);
                }
            }
            removeTotal = list.size()-advertPos.size();
        }

        //卖出
        if (type==OrderTypeEnum.Sell.getCode()) {
            list.forEach(n->{

                if (TransPairLegalEnum.AEC2USD.getCode().equals(transactionPair)){
                    n.setMaxV(MathUtils.mul8p(n.getMaxV(),new BigDecimal(iTaxRateService.cny2usd())));
                    n.setMinV(MathUtils.mul8p(n.getMinV(),new BigDecimal(iTaxRateService.cny2usd())));
                    n.setUnitPrice(MathUtils.mul8p(n.getUnitPrice(),new BigDecimal(iTaxRateService.cny2usd())));
                }

                if (TransPairLegalEnum.AEC2EUR.getCode().equals(transactionPair)){
                    n.setMaxV(MathUtils.mul8p(n.getMaxV(),new BigDecimal(iTaxRateService.cny2eur())));
                    n.setMinV(MathUtils.mul8p(n.getMinV(),new BigDecimal(iTaxRateService.cny2eur())));
                    n.setUnitPrice(MathUtils.mul8p(n.getUnitPrice(),new BigDecimal(iTaxRateService.cny2eur())));
                }

                if (TransPairLegalEnum.AEC2HKD.getCode().equals(transactionPair)){
                    n.setMaxV(MathUtils.mul8p(n.getMaxV(),new BigDecimal(iTaxRateService.cny2hkd())));
                    n.setMinV(MathUtils.mul8p(n.getMinV(),new BigDecimal(iTaxRateService.cny2hkd())));
                    n.setUnitPrice(MathUtils.mul8p(n.getUnitPrice(),new BigDecimal(iTaxRateService.cny2hkd())));

                }

                if (n.getCNY()==0 || n.getUSD()==0 || n.getHKD()==0 ||n.getEUR()==0) {
                    n.setIsBank(0);
                } else {
                    n.setIsBank(1);
                }


                //查询近30天完成的订单

                Integer completedOrder = advertCustomMapper.getCompetedOrder(n.getAdvertUserId(),time);
                if (completedOrder==null)
                    completedOrder = 0;
                n.setCompletedOrder(completedOrder);

            });

            //数量为0的广告不显示
            for (AdvertPo advertPo:list) {
                if (advertPo.getEnableStock().intValue()!=0){
                    advertPos.add(advertPo);
                }

            }
            removeTotal = list.size()-advertPos.size();
        }


        if (list.size()>0){
            total = advertCustomMapper.countAdvertList1(type, transactionPair);
            list = null;
        }

        return new AdvertPageVo(page,rows,total- removeTotal,advertPos);
    }

    /**
     * 1. 生成订单
     * 2. 修改广告可用AEC数量
     */
//    @Transactional
//    public String buy(AdvertBuyAndSellDto dto) {
//        log.info("==>user buy:start<==");
//        String orderId = "";
//        String userId = ShiroUtils.getShiroUserId();
//        //判断用户是否存在未付款的订单
//        int unpaidCount = orderCustomMapper.countUnpaid(userId,OrderTypeEnum.Buy.getCode());
//        log.info("==>user buy: unpaidCount={}<==",unpaidCount);
//        A.check(unpaidCount>0,"您有一笔未付款订单，请先处理该订单");
//
//        log.info("==>user buy: locking<==");
//        String result = iRedisTemplateSerivice.lock(key,userId,expire);
//        log.info("==>user buy: lock result={}<==",result);
//        log.info("==>user buy: lock end<==");
//        if("OK".equalsIgnoreCase(result)){
//            try {
//                //校验用户AEC账户是否存在
//                WalletInfo userWalletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.AEC.getCode());
//                A.check(userWalletInfo==null,"网络异常");
//                Advert advert = advertMapper.selectByPrimaryKey(dto.getId());
//                A.check(advert==null,"当前广告不存在");
//                A.check(advert.getStatus()== AdvertStatusEnum.downShelf.getCode(),"当前广告已下架");
//                A.check(advert.getType() != AdvertTypeEnum.Sell.getCode(),"不支持的购买类型");
//                BigDecimal amount = dto.getAmount();  //购买数量
//                //校验购买数量是否超过范围
//                A.check(isRange(advert, amount),"购买超过限制范围");
//
//                //校验数量不能超过数量(余额/保证金余额)
//                BigDecimal bondBalance = getbondBalance(advert.getCreateUser());//计算保证金余额
//                WalletInfo yinshangWalletInfo =  walletInfoCustomMapper.getByUserIdAndType(advert.getCreateUser(), WalletTypeEnum.AEC.getCode());
//                if (MathUtils.greatForBg(bondBalance,yinshangWalletInfo.getBalance())){
//                    A.check(MathUtils.greatForBg(amount,yinshangWalletInfo.getBalance()),"购买超过数量");
//                }else{
//                    A.check(MathUtils.greatForBg(amount,bondBalance),"购买超过数量");
//                }
//
//                if (dto.getType()==1) {
//                    //全部买入
//                    amount = bondBalance;
//                    A.check(amount.intValue()==0,"已售完");
//                    //生成订单，修改广告可用AEC数量
//                    orderId = generateBuyOrder(dto, advert, amount);
//
//                }else if(dto.getType()==0){
//                    A.check(MathUtils.greatForBg(amount,bondBalance),"您购买的数量超过广告数量");
//                    //生成订单，修改广告可用AEC数量
//                    orderId = generateBuyOrder(dto, advert, amount);
//                }
//            }catch (Exception e){
//                log.info("==>user buy:exception<==");
//                iRedisTemplateSerivice.unlock(key, userId);
//                throw new ServiceException(500,e.getMessage());
//            }
//            iRedisTemplateSerivice.unlock(key, userId);
//            log.info("==>user buy: end<==");
//            return orderId;
//        }else{
//            log.info("==>user buy:lock exist excpetion<==");
//            throw new ServiceException(500, "网络异常");
//        }
//    }


    /**
     * 1. 生成订单
     * 2. 修改广告可用AEC数量
     */
    @Transactional
    @Override
    public String buy1(AdvertBuyAndSellDto dto) {
        log.info("==>user buy:start<==");
        String orderId = "";
        String userId = ShiroUtils.getShiroUserId();
        //判断用户是否存在未付款的订单
        int unpaidCount = orderCustomMapper.countUnpaid(userId,OrderTypeEnum.Buy.getCode());
        log.info("==>user buy: unpaidCount={}<==",unpaidCount);
        A.check(unpaidCount>0,"您有一笔未付款订单，请先处理该订单");

        //校验用户AEC账户是否存在
        WalletInfo userWalletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.AEC.getCode());
        A.check(userWalletInfo==null,"钱包地址不存在");
        Advert advert = advertMapper.selectByPrimaryKey(dto.getId());
        A.check(advert==null,"当前广告不存在");
        A.check(advert.getStatus()== AdvertStatusEnum.downShelf.getCode(),"当前广告已下架");
        A.check(advert.getType() != AdvertTypeEnum.Sell.getCode(),"不支持的购买类型");
        BigDecimal amount = dto.getAmount();  //购买数量
        //校验购买数量是否超过范围
        A.check(isRange(advert, amount),"购买超过限制范围");

        log.info("==>user buy: locking<==");
        boolean result = RedissLockUtil.tryLock(key, waitTime, leaseTime);
        log.info("==>user buy: lock result={}<==",result);
        if(result){
            try {
                //校验数量不能超过数量(余额/保证金余额)
                WalletInfo yinshangWalletInfo =  walletInfoCustomMapper.getByUserIdAndType(advert.getCreateUser(), WalletTypeEnum.AEC.getCode());
                BigDecimal bondBalance = getbondBalance(advert.getCreateUser(),yinshangWalletInfo);//计算保证金余额
                if (MathUtils.greatForBg(bondBalance,yinshangWalletInfo.getBalance())){
                    A.check(MathUtils.greatForBg(amount,yinshangWalletInfo.getBalance()),"购买超过数量");
                }else{
                    A.check(MathUtils.greatForBg(amount,bondBalance),"购买超过数量");
                }

                if (dto.getType()==1) {
                    //全部买入
                    amount = bondBalance;
                    A.check(amount.intValue()==0,"已售完");
                    //生成订单，修改广告可用AEC数量
                    orderId = generateBuyOrder(dto, advert, amount);

                }else if(dto.getType()==0){
                    A.check(MathUtils.greatForBg(amount,bondBalance),"您购买的数量超过广告数量");
                    //生成订单，修改广告可用AEC数量
                    orderId = generateBuyOrder(dto, advert, amount);
                }
            }catch (Exception e){
                log.info("==>user buy:exception<==");
                RedissLockUtil.unlock(key);
                throw new ServiceException(500,e.getMessage());
            }
            RedissLockUtil.unlock(key);
            log.info("==>user buy: end<==");
            return orderId;
        }else{
            log.info("==>user buy:lock exist excpetion<==");
            throw new ServiceException(500, "网络异常");
        }
    }

    private boolean isRange(Advert advert, BigDecimal amount) {
        BigDecimal money = MathUtils.mul8p(amount, advert.getUnitPrice());
        if (MathUtils.lessForBg(money,advert.getMinValue())){
            return true;
        }
        if (MathUtils.greatForBg(money, advert.getMaxValue())) {
            return true;
        }
        return false;
    }

    //计算保证金余额
    private BigDecimal getbondBalance(String advertUserId, WalletInfo yinshangWalletInfo) {
         BigDecimal yinshangbond = yinshangWalletInfo.getBond();//银商保证金
        //查询银商卖出订单AEC数量（未付款、已付款、申述中）
//        int aecTotal = orderCustomMapper.getTotalQuantityYinShang(advertUserId);
        BigDecimal aecTotal = new BigDecimal(0);
        YinshangQuantity yinshangQuantity = yinshangQuantityCustomMapper.getYinshangQuantityById(advertUserId);
        if (yinshangQuantity!=null)
            aecTotal = yinshangQuantity.getQuantity();
        return MathUtils.sub8p(yinshangbond, aecTotal);
    }

    public String generateBuyOrder(AdvertBuyAndSellDto dto, Advert advert, BigDecimal amount) {
        Date currentTime = DateUtils.getCurentTime();
        Order order = new Order();
        order.setId(UUIDUtils.getUUID()).setTransactionPair(dto.getTransactionPair()).setType(OrderTypeEnum.Buy.getCode());
        order.setAdvertId(advert.getId()).setUserId(ShiroUtils.getShiroUserId()).setRole(ShiroUtils.getShiroUser().getRole());
        order.setCreateTime(currentTime);
        order.setAdveruUserId(advert.getCreateUser());
        String taxrate = "1";
        if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2USD.getCode())){
            taxrate = iTaxRateService.cny2usd();
        }else if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2EUR.getCode())){
            taxrate = iTaxRateService.cny2eur();
        }else if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2HKD.getCode())){
            taxrate = iTaxRateService.cny2hkd();
        }
        BigDecimal unitPrice = MathUtils.mul8p(new BigDecimal(taxrate),advert.getUnitPrice());
        order.setUnitPrice(unitPrice);
        order.setQuantity(amount);
        order.setMoney(MathUtils.mul8p(amount,unitPrice));
        order.setTaxRate(taxrate);
        order.setOrderNo(serialCodeGenerator.getNext());
        orderMapper.insertSelective(order);
        YinshangQuantity yinshangQuantity = yinshangQuantityCustomMapper.getYinshangQuantityById(advert.getCreateUser());
        if (yinshangQuantity == null){
            yinshangQuantityCustomMapper.insertYinshangQuantity(advert.getCreateUser(),order.getQuantity());
        }else {
            yinshangQuantityCustomMapper.updateYinshangQuantityById(advert.getCreateUser(), order.getQuantity());
        }
        return order.getId();
    }

    /**
     * 1. 生成订单
     * 2. 冻结用户卖出的AEC数量
     * 3. 生成冻结记录
     */
    @Transactional
    @Override
    public String sell(AdvertBuyAndSellDto dto) {
        String ordeId = "";
        String userId = ShiroUtils.getShiroUserId();
        //校验交易密码
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(!PasswordUtil.check(dto.getPayPassword(), user.getPayPassword(), user.getPaySalt()), "密码错误!");
        //校验用户是否存在支付信息
        checkUserPayInfo(userId,dto.getTransactionPair());

        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.AEC.getCode());
        A.check(walletInfo==null,"钱包地址不存在");

        Advert advert = advertMapper.selectByPrimaryKey(dto.getId());
        A.check(advert==null,"当前广告不存在");
        A.check(advert.getStatus()== AdvertStatusEnum.downShelf.getCode(),"当前广告已下架");
        A.check(advert.getType() != AdvertTypeEnum.Buy.getCode(),"不支持的购买类型");

        BigDecimal amount = dto.getAmount();

        //校验卖出数量是否超过范围
        A.check(isRange(advert, amount),"卖出超限");

        if (dto.getType()==2){
            //全部卖出
            amount = walletInfo.getBalance();
            ordeId = generateSellOrder(dto, advert, amount,walletInfo);

        }else if(dto.getType()==0){
            A.check(MathUtils.greatOrEqualForBg(amount,walletInfo.getBalance()),"余额不足");
            ordeId = generateSellOrder(dto, advert, amount,walletInfo);
        }
        return ordeId;
    }

    private void checkUserPayInfo(String userId,String transactionPair) {
        YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);
        if (yinshangIsPay==null){
            throw new ServiceException(StatusCodeEnum.JumpPayInfo.getCode(),"您未设置收款方式，是否去设置");
        }

        if (
            yinshangIsPay.getCny()==1 &&
            yinshangIsPay.getAlipay() ==1 &&
            yinshangIsPay.getEur()==1 &&
            yinshangIsPay.getUsd()==1 &&
            yinshangIsPay.getHkd() ==1 &&
            yinshangIsPay.getPaypal() ==1 &&
            yinshangIsPay.getSwift() ==1 &&
            yinshangIsPay.getXilian() ==1
        )
        {
            throw new ServiceException(StatusCodeEnum.JumpPayInfo.getCode(),"您未设置收款方式，是否去设置");
        }

        if (TransPairLegalEnum.AEC2CNY.getCode().equals(transactionPair)){
            if (yinshangIsPay.getCny()==1 &&
                yinshangIsPay.getAlipay() ==1 &&
                yinshangIsPay.getPaypal() ==1 &&
                yinshangIsPay.getSwift() ==1 &&
                yinshangIsPay.getXilian() ==1
            )
            {
                throw new ServiceException(StatusCodeEnum.JumpPayInfo.getCode(),"您未设置"+WalletTypeEnum.CNY.getCode()+"收款方式，是否去设置");
            }
        }

        if (TransPairLegalEnum.AEC2USD.getCode().equals(transactionPair)){
            if (yinshangIsPay.getUsd()==1 &&
                yinshangIsPay.getPaypal() ==1 &&
                yinshangIsPay.getSwift() ==1 &&
                yinshangIsPay.getXilian() ==1
            )
            {
                throw new ServiceException(StatusCodeEnum.JumpPayInfo.getCode(),"您未设置"+WalletTypeEnum.USD.getCode()+"收款方式，是否去设置");
            }
        }

        if (TransPairLegalEnum.AEC2EUR.getCode().equals(transactionPair)){
            if (yinshangIsPay.getEur()==1 &&
                yinshangIsPay.getPaypal() ==1 &&
                yinshangIsPay.getSwift() ==1 &&
                yinshangIsPay.getXilian() ==1
            )
            {
                throw new ServiceException(StatusCodeEnum.JumpPayInfo.getCode(),"您未设置"+WalletTypeEnum.EUR.getCode()+"收款方式，是否去设置");
            }
        }

        if (TransPairLegalEnum.AEC2HKD.getCode().equals(transactionPair)){
            if (yinshangIsPay.getHkd()==1 &&
                yinshangIsPay.getPaypal() ==1 &&
                yinshangIsPay.getSwift() ==1 &&
                yinshangIsPay.getXilian() ==1
            )
            {
                throw new ServiceException(StatusCodeEnum.JumpPayInfo.getCode(),"您未设置"+WalletTypeEnum.HKD.getCode()+"收款方式，是否去设置");
            }
        }



    }

    public String generateSellOrder(AdvertBuyAndSellDto dto, Advert advert, BigDecimal amount,WalletInfo fromWalletInfo) {
        Date currentTime = DateUtils.getCurentTime();
        Order order = new Order();
        order.setId(UUIDUtils.getUUID()).setTransactionPair(dto.getTransactionPair()).setType(OrderTypeEnum.Sell.getCode());
        order.setAdvertId(advert.getId()).setUserId(ShiroUtils.getShiroUserId()).setRole(ShiroUtils.getShiroUser().getRole());

        order.setCreateTime(currentTime);
        order.setAdveruUserId(advert.getCreateUser());
        String taxrate = "1";
        if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2CNY.getCode())){

        }else if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2USD.getCode())){
            taxrate = iTaxRateService.cny2usd();
        }else if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2EUR.getCode())){
            taxrate = iTaxRateService.cny2eur();
        }else if (dto.getTransactionPair().equalsIgnoreCase(TransPairLegalEnum.AEC2HKD.getCode())){
            taxrate = iTaxRateService.cny2hkd();
        }
        order.setTaxRate(taxrate);

        BigDecimal unitPrice = MathUtils.mul8p(new BigDecimal(taxrate),advert.getUnitPrice());
        order.setMoney(MathUtils.mul8p(amount,unitPrice));
        order.setUnitPrice(unitPrice).setQuantity(amount);
        order.setOrderNo(serialCodeGenerator.getNext());
        orderMapper.insertSelective(order);

        //2. 冻结用户卖出的AEC数量
        int i = walletInfoCustomMapper.updateBalanceAndFrozenBlanceById(amount,fromWalletInfo.getId());
        A.check(i==0,"余额不足");

        //获取手续费
        BigDecimal fee = iGlobalParameterService.getFee(amount,WithdrawFeeEnum.c2cfee.getCode());

        //3. 生成冻结记录
        WalletInfo to = walletInfoCustomMapper.getByUserIdAndType(advert.getCreateUser(), WalletTypeEnum.AEC.getCode());
        String toUserId = to.getUserId();
        String toAddress = to.getAddress();
        String fromUserId = fromWalletInfo.getUserId();
        String fromAddress = fromWalletInfo.getAddress();
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setId(UUIDUtils.getUUID()).setTransactionPair(TransPairEnum.AEC2AEC.getCode());
        walletRecord.setFromId(fromUserId).setToId(toUserId);
        walletRecord.setFromAddress(fromAddress).setToAddress(toAddress);
        walletRecord.setFromAmount(amount);
        //银商到账AEC=用户卖出AEC-手续费
        walletRecord.setToAmount(MathUtils.sub8p(amount,fee));
        walletRecord.setFee(fee);
        walletRecord.setOperate(WalletOperateEnum.six.getCode()).setDescri("冻结-用户卖出AEC");
        walletRecord.setSource(order.getId());
        walletRecord.setCreateTime(currentTime);
        walletRecordMapper.insertSelective(walletRecord);

        //4.更新广告可用数量(减)
        advertCustomMapper.updateAdvertEnableStockById(MathUtils.mul8p(order.getQuantity(),new BigDecimal(-1)), order.getAdvertId());

        return order.getId();
    }

    private Date getTime(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -30);
        return now.getTime();
    }
}
