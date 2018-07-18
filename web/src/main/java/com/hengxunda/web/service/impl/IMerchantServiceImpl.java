package com.hengxunda.web.service.impl;

import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Common.UserRoleConstant;
import com.hengxunda.common.Common.WebConstant;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.Page;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.*;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.web.*;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.web.service.IMerchantService;
import com.hengxunda.web.utils.MessageUtil;
import com.hengxunda.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
@Slf4j
@Service
public class IMerchantServiceImpl implements IMerchantService {

    @Autowired
    private MerchantCustomMapper merchantCustomMapper;
    @Autowired
    private WalletInfoMapper walletInfoMapper;
    @Autowired
    private UserBankInfoMapper userBankInfoMapper;
    @Autowired
    private UserReceiveMapper userReceiveMapper;
    @Autowired
    private MerchantApplyMapper merchantApplyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdvertCustomMapper advertCustomMapper;
    @Autowired
    private GlobalParameterCustomMapper globalParameterCustomMapper;
    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;
    @Autowired
    private BondApplyCustomMapper bondApplyCustomMapper;
    @Autowired
    private WalletRecordMapper walletRecordMapper;
    @Autowired
    private IStringRedisService stringRedisService;
    @Autowired
    private UserLoginCumstomMapper userLoginCumstomMapper;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private YinshangApplyMapper yinshangApplyMapper;


    /**
     * 保证金相关
     */
    @Override
    public BondApplyListVo getBondApplys(String mobile, String name, int type, int status, BigDecimal change, BigDecimal original, Page page) {
        BondApplyPo bondApplyPo = getBondApplyPo(mobile, name, type, status, change, original);
        List<BondApplyPo> bondApplyPos = bondApplyCustomMapper.getBondApplySelect(bondApplyPo, new Page(page.getPageNo(), page.getLimit()));
        int count = bondApplyCustomMapper.countBondApplySelect(bondApplyPo);
        BondApplyListVo bondApplyListVo = new BondApplyListVo();
        bondApplyListVo.setBondApplyPos(bondApplyPos).setTotal(count);
        return bondApplyListVo;
    }

    @Override
    public List<BondApplyPo> downloadBondApplys(String mobile, String name, int type, int status, BigDecimal change, BigDecimal original, Date beginTime, Date endTime) {
        BondApplyPo bondApplyPo = getBondApplyPo(mobile, name, type, status, change, original);
        bondApplyPo.setBeginTime(beginTime).setEndTime(endTime);
        List<BondApplyPo> bondApplyPos = bondApplyCustomMapper.downloadBondApplys(bondApplyPo);
        return bondApplyPos;
    }

    @Override
    @Transactional
    public void reviewBondApply(String id, String userId, Integer status, Integer type, BigDecimal amount, String reason) {
        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdOrStatusOrType(userId, 0, WalletTypeEnum.AEC.name());
        A.check(Objects.isNull(walletInfo), "钱包信息不存在!");

        if (type == 1) {// 降低保证金
            if (status == 1) {// 审核通过
                walletInfoCustomMapper.updateLessBondByUserId(amount, userId);
                this.insertSelective(userId, walletInfo.getAddress(), amount, WalletOperateEnum.nine);
                messageUtil.messageWrite(userId, NoticeTypeEnum.notice.getCode(), "降低保证金审核通过", null, "恭喜，您申请的降低保证金已审核通过，系统已将您降低的保证金划转到您的账户余额。");
            } else {
                messageUtil.messageWrite(userId, NoticeTypeEnum.notice.getCode(), "降低保证金审核不通过", null, "抱歉，您申请的降低保证金审核不通过！原因：您有未处理完成的订单或您有正在处理的纠纷。");
            }
        } else if (type == 2) {// 提取保证金
            if (status == 1) {// 审核通过
                walletInfoCustomMapper.updateLessBondByUserId(walletInfo.getBond(), userId);
                this.insertSelective(userId, walletInfo.getAddress(), walletInfo.getBond(), WalletOperateEnum.sixteen);
                stringRedisService.delete(RedisConstant.wapp_token + userLoginCumstomMapper.getUserLoginByUserAndSource(userId, UserRoleEnum.yinshang.getCode()).getToken());
                userMapper.updateByPrimaryKeySelective(new User().setId(userId).setRole(UserRoleEnum.ordinaryUser.getCode()));
                MerchantApplyPo po = merchantApplyMapper.getYsApplyByUserId(userId);
                YinshangApply yinshangApply = new YinshangApply();
                yinshangApply.setId(po.getId()).setDataFlagStatus(1);
                yinshangApplyMapper.updateByPrimaryKeySelective(yinshangApply);

            } else {
                messageUtil.messageWrite(userId, NoticeTypeEnum.notice.getCode(), "提取保证金审核不通过", null, "抱歉，您申请的提取保证金审核不通过！原因：您有未处理完成的订单或您有正在处理的纠纷。");
            }
        }

        bondApplyCustomMapper.reviewBondApply(id, status, reason);
    }

    private void insertSelective(String userId, String address, BigDecimal amount, WalletOperateEnum operateEnum) {
        Date now = DateUtils.getCurentTime();
        WalletRecord walletRecord = new WalletRecord()
                .setId(UUIDUtils.getUUID())
                .setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                .setFromId(userId)
                .setToId(userId)
                .setFromAddress(address)
                .setToAddress(address)
                .setFromAmount(amount)
                .setToAmount(amount)
                .setOperate(operateEnum.getCode())
                .setDescri(operateEnum.getValue(operateEnum))
                .setTransType(TransTypeEnum.BLOCKINTRADE.getCode())
                .setCreateTime(now)
                .setUpdateTime(now);
        walletRecordMapper.insertSelective(walletRecord);
    }

    private BondApplyPo getBondApplyPo(String mobile, String name, int type, int status, BigDecimal change, BigDecimal original) {
        BondApplyPo bondApplyPo = new BondApplyPo();
        bondApplyPo.setType(type).setStatus(status);
        if (mobile != null && !mobile.equals("")) {
            bondApplyPo.setPhone(mobile);
        }
        if (name != null && !name.equals("")) {
            bondApplyPo.setName(name);
        }
        if (change != null) {
//            bondApplyPo.setBond(change.toString());
        }
        if (original != null) {
            bondApplyPo.setTotalBond(original);
        }
        return bondApplyPo;
    }

    /**
     * 广告相关
     */
    @Override
    public AdvertListVo getAdvertWebs(
            String mobile, String name, BigDecimal bond, int type, int status,
            BigDecimal minValue, BigDecimal price, Date createTime, Page page) {

        AdvertWebPo advertWebPo = getAdvertWebPo(mobile, name, bond, minValue, price);
        advertWebPo.setType(type).setStatus(status);
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(createTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            advertWebPo.setBeginTime(createTime).setEndTime(c.getTime());
        }

        List<AdvertWebPo> list = advertCustomMapper.getAdvertsForWeb(advertWebPo, new Page(page.getPageNo(), page.getLimit()));
        int count = advertCustomMapper.countAdvertsForWeb(advertWebPo);
        AdvertListVo advertListVo = new AdvertListVo();
        advertListVo.setAdvertPos(list).setTotal(count);
        return advertListVo;
    }

    @Override
    public List<AdvertWebPo> downloadAdverts(String mobile, String name, BigDecimal bond, int type, int status,
                                             BigDecimal minValue, BigDecimal price, Date beginTime, Date endTime) {

        AdvertWebPo advertWebPo = getAdvertWebPo(mobile, name, bond, minValue, price);
        advertWebPo.setBeginTime(beginTime).setEndTime(endTime).setType(type).setStatus(status);

        List<AdvertWebPo> advertWebPos = advertCustomMapper.getAdvertsForDownload(advertWebPo);
        return advertWebPos;
    }

    private AdvertWebPo getAdvertWebPo(String mobile, String name, BigDecimal bond, BigDecimal minValue, BigDecimal price) {
        AdvertWebPo advertWebPo = new AdvertWebPo();
        if (mobile != null && !mobile.equals("")) {
            advertWebPo.setPhone(mobile);
        }
        if (name != null && !name.equals("")) {
            advertWebPo.setName(name);
        }
        if (bond != null) {
            advertWebPo.setBond(bond);
        }

        if (minValue != null) {
            advertWebPo.setMinValue(minValue);
        }
        if (price != null) {
            advertWebPo.setUnitPrice(price);
        }

        return advertWebPo;
    }

    /**
     * 申请相关
     */
    @Override
    public List<MerchantApplyPo> downlodApllys(String uid, String mobile, String name, int status, Date beginTime, Date endTime) {
        MerchantApplyPo merchantApplyPo = new MerchantApplyPo();
        merchantApplyPo.setBeginTime(beginTime).setEndTime(endTime).setStatus(status);
        if (uid != null && !uid.equals("")) {
            merchantApplyPo.setUid(uid);
        }
        if (mobile != null && !mobile.equals("")) {
            merchantApplyPo.setPhone(mobile);
        }
        if (name != null && !name.equals("")) {
            merchantApplyPo.setName(name);
        }

        return merchantApplyMapper.downloadApplys(merchantApplyPo);
    }

    @Override
    public MerchantApplyVo getMerchantApplys(String uid, String mobile, String name, int status, Page page) {
        MerchantApplyPo merchantApplyPo = new MerchantApplyPo();
        merchantApplyPo.setStatus(status);
        if (uid != null && !uid.equals("")) {
            merchantApplyPo.setUid(uid);
        }
        if (mobile != null && !mobile.equals("")) {
            merchantApplyPo.setPhone(mobile);
        }
        if (name != null && !name.equals("")) {
            merchantApplyPo.setName(name);
        }

        List<MerchantApplyPo> merchantApplyPos = merchantApplyMapper.getMerchantApplysSelect(merchantApplyPo, new Page(page.getPageNo(), page.getLimit()));
        int count = merchantApplyMapper.countMerchantApply(merchantApplyPo);
        MerchantApplyVo merchantApplyVo = new MerchantApplyVo();
        merchantApplyVo.setMerchantApplyPos(merchantApplyPos).setTotal(count);
        return merchantApplyVo;
    }

    @Override
    public MerchantDetailVo getMerchantApplyInfoById(String id) {
        return getMerchantDetailById(id, WebConstant.MERCHANT_APPLY_DETAIL_TYPE);
    }

    @Override
    @Transactional
    public int reviewMerchantApply(String id, int status) throws Exception {
        int result;
        //更改申请表状态
        result = merchantApplyMapper.reviewMerchantApply(id, status);
        if (result != 1) {
            log.info("更新银商申请表失败");
            throw new Exception("更新银商申请表失败，请检查申请状态是否正确");
        }
        //通过审核
        if (status == 2) {
            //更新保证金
            String bond = globalParameterCustomMapper.selectByCronKey(WebConstant.MERCHANT_BOND_MINVALUE).getCronValue();
            BigDecimal bondAmount = new BigDecimal(bond);
            result = walletInfoCustomMapper.updateBalanceAndBondByUserId(bondAmount, id);
            if (result != 1) {
                log.info("更新银商申请者保证金失败");
                throw new Exception("更新银商申请者保证金失败");
            }
            User u = new User();
            u.setId(id).setRole(UserRoleConstant.USER_ROLE_MERCHANT);
            result = userMapper.updateByPrimaryKeySelective(u);
            if (result != 1) {
                log.info("更新银商申请者用户角色失败");
                throw new Exception("更新银商申请者用户角色失败");
            }
            //写入钱包记录（增加保证金，减少可以余额）
            String coinType = WalletTypeEnum.AEC.getCode();
            WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdAndType(id, coinType);
            A.check(Objects.isNull(walletInfo), "钱包地址不存在！");
            WalletRecord walletRecord = new WalletRecord();
            walletRecord.setId(UUIDUtils.getUUID()).setFromId(id).setToId(id).setFromAddress(walletInfo.getAddress())
                    .setToAddress(walletInfo.getAddress()).setFromAmount(bondAmount).setTransType(TransTypeEnum.BLOCKINTRADE.getCode()).setSource("system")
                    .setCreateTime(DateUtils.getCurentTime()).setOperate(WalletOperateEnum.seventeen.getCode()).setTransactionPair(TransPairEnum.AEC2AEC.getCode())
                    .setDescri("成为银商").setToAmount(bondAmount);
            result = walletRecordMapper.insertSelective(walletRecord);
            if (result != 1) {
                log.info("银商审核写入钱包记录失败");
                throw new Exception("银商审核写入钱包记录失败");
            }
            //写入通知消息
            messageUtil.messageWrite(id, NoticeTypeEnum.notice.getCode(), "您已开通银商服务！", null, "恭喜，您已经成功开通了银商服务，赶紧去发布广告进行AEC币的买卖交易吧！");
        }


        return result;
    }

    /**
     * 银商列表相关
     */
    @Override
    public List<MerchantVo> downLoadExcel(String userId, String mobile, String name, String nickName, Date beginTime, Date endTime, HttpServletResponse response) {

        //获取数据
        MerchantPo merchantPo = new MerchantPo();
        merchantPo.setRole(UserRoleConstant.USER_ROLE_MERCHANT);
        merchantPo.setCreateBeginTime(beginTime).setCreateEndTime(endTime);
        if (mobile != null && !mobile.equals("")) {
            merchantPo.setPhone(mobile);
        }
        if (name != null && !name.equals("")) {
            merchantPo.setName(name);
        }
        if (nickName != null && !nickName.equals("")) {
            merchantPo.setNickName(nickName);
        }
        if (userId != null && !userId.equals("")) {
            merchantPo.setUid(userId);
        }


        List<MerchantPo> merchantPos = merchantCustomMapper.getMerchantsForDownload(merchantPo);

        List<MerchantVo> merchantVos = MerchantListVo.format(merchantPos).getMerchantVos();
        return merchantVos;
    }

    @Override
    public MerchantDetailVo getMerchantDetailById(String id) {
        return getMerchantDetailById(id, WebConstant.MERCHANT_DETAIL_TYPE);
    }


    @Override
    public MerchantListVo getMerchants(String userId, String mobile, String name, String nickName, String creatTime, Page page) {
        MerchantPo merchantPo = new MerchantPo();
        merchantPo.setRole(UserRoleConstant.USER_ROLE_MERCHANT);
        if (userId != null && !userId.equals("")) {
            merchantPo.setUid(userId);
        }
        if (mobile != null && !mobile.equals("")) {
            merchantPo.setPhone(mobile);
        }
        if (name != null && !name.equals("")) {
            merchantPo.setName(name);
        }
        if (nickName != null && !nickName.equals("")) {
            merchantPo.setNickName(nickName);
        }
        if (creatTime != null && !creatTime.equals("")) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begin = sd.parse(creatTime);
                Date end = sd.parse(creatTime.replace("00:00:00", "23:59:59"));
                merchantPo.setCreateBeginTime(begin).setCreateEndTime(end);
            } catch (ParseException e) {
                log.info("时间格式化失败");
            }
        }
        List<MerchantPo> poList = merchantCustomMapper.getMerchantSelect(merchantPo, new Page(page.getPageNo(), page.getLimit()));
        int count = merchantCustomMapper.countMerchantSelect(merchantPo);
        MerchantListVo merchantListVo = MerchantListVo.format(poList);
        merchantListVo.setTotal(count);

        return merchantListVo;
    }

    //获取云商详细信息
    public MerchantDetailVo getMerchantDetailById(String id, int type) {
        MerchantDetailVo merchantDetailVo = new MerchantDetailVo();

        //银商基本信息
        MerchantPo merchantPo = merchantCustomMapper.selectMerchantPoById(id);
        MerchantTradeInfo merchantTradeInfo = new MerchantTradeInfo();
        if (type == 0) {
            merchantTradeInfo = merchantCustomMapper.getTradeInfo(id);
        }

        merchantDetailVo.setMerchantVo(MerchantVo.format(merchantPo, merchantTradeInfo));

        //获取钱包信息
        WalletInfoExample walletInfoExample = new WalletInfoExample();
        walletInfoExample.or().andUserIdEqualTo(id);
        List<WalletInfo> walletInfos = walletInfoMapper.selectByExample(walletInfoExample);
        merchantDetailVo.setWalletInfoVos(WalletInfoVo.formatList(walletInfos));

        //支付方式信息
        UserBankInfoExample userBankInfoExample = new UserBankInfoExample();
        userBankInfoExample.or().andUserIdEqualTo(id);
        List<UserBankInfo> userBankInfos = userBankInfoMapper.selectByExample(userBankInfoExample);
        List<BankInfoVo> bankInfoVos = BankInfoVo.formatBankList(userBankInfos);

        UserReceiveExample userReceiveExample = new UserReceiveExample();
        userReceiveExample.or().andUserIdEqualTo(id);
        List<UserReceive> userReceives = userReceiveMapper.selectByExample(userReceiveExample);
        List<BankInfoVo> bankInfoVos1 = BankInfoVo.formatReceiveList(userReceives);

        bankInfoVos.addAll(bankInfoVos1);
        merchantDetailVo.setBankInfoVos(bankInfoVos);

        return merchantDetailVo;
    }

}
