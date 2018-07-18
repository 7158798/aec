package com.hengxunda.wapp.service.impl;

import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.UserLoginMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.YinshangApplyMapper;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.dao.po.web.MerchantApplyPo;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.ISmsService;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.wapp.dto.*;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.IUserService;
import com.hengxunda.wapp.vo.LoginInfoVo;
import com.hengxunda.wapp.vo.PayVo;
import com.hengxunda.wapp.vo.UserInfoVo;
import com.hengxunda.wapp.vo.YsApplyVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Value("${token.expireTime}")
    private int TOKENEXPIRETIME;


    @Autowired
    UserMapper userMapper;

    @Autowired
    WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    OrderCustomMapper orderCustomMapper;

    @Autowired
    ISmsService smsService;

    @Autowired
    UserLoginMapper userLoginMapper;

    @Autowired
    private UserCustomMapper userCustomMapper;

    @Autowired
    private UserLoginCumstomMapper userLoginCumstomMapper;

    @Autowired
    private IGlobalParameterService iGlobalParameterService;

    @Autowired
    private YinshangApplyMapper yinshangApplyMapper;

    @Autowired
    private IStringRedisService iStringRedisService;

    @Autowired
    private MerchantApplyMapper merchantApplyMapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private YinshangIsPayCustomMapper yinshangIsPayCustomMapper;

    @Autowired
    private AppVersionCustomMapper appVersionCustomMapper;

    @Override
    public void switchLoginStatus(Integer loginStatus) {
        this.userMapper.updateByPrimaryKeySelective(new User().setId(ShiroUtils.getShiroUserId()).setLoginStatus(loginStatus));
    }

    @Override
    public UserInfoVo getById() {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        return new UserInfoVo()
                .setUserId(user.getId())
                .setName(user.getName())
                .setNickName(user.getNickName())
                .setAvatar(user.getAvatar())
                .setBalance(getWalletInfo(user).getBalance())
                .setBond(getWalletInfo(user).getBond())
                .setTradingNumber(orderCustomMapper.count(new Order().setAdveruUserId(user.getId()).setStatus(OrderStatusEnum.completed.getCode())));
    }

    private WalletInfo getWalletInfo(User user) {
        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdOrStatusOrType(user.getId(), 0, WalletTypeEnum.AEC.name());
        return walletInfo != null ? walletInfo : new WalletInfo();
    }

    @Override
    public void checkLoginPass(String password) {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        A.check(!PasswordUtil.check(password, user.getPassword(), user.getSalt()), "登录密码验证错误!");
    }

    @Override
    public void setPayPass(PayPasswordDto payPasswordDto) {
        smsService.checkSmsCode(getUser().getPhone(), SmsTypeEnum.UpdatePayPwd.getCode(), payPasswordDto.getCaptchaCode());

        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        String paySalt = PasswordUtil.getSalt();
        user.setPayPassword(PasswordUtil.encrypt(payPasswordDto.getPassword(), paySalt)).setPaySalt(paySalt);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void resetLoginPass(LoginPasswordDto loginPasswordDto) {
        A.check(StringUtils.isBlank(loginPasswordDto.getOldPassword()), "旧登录密码不能为空");
        A.check(StringUtils.isBlank(loginPasswordDto.getPassword()), "新登录密码不能为空");
        iUserService.checkLoginPass(loginPasswordDto.getOldPassword());
        userMapper.updateByPrimaryKeySelective(new User().setId(ShiroUtils.getShiroUserId()).setPassword(encrypt(loginPasswordDto.getPassword(), SmsTypeEnum.UpdateLoginPwd)));
    }

    @Override
    public void logOut() {
        iStringRedisService.delete(RedisConstant.wapp_token + RequestUtils.getToken());
//        UserLogin userLogin = new UserLogin().setExpireTime(DateUtils.getCurentTime()).setStatus(1);
//        UserLoginExample example = new UserLoginExample();
//        example.createCriteria().andUserIdEqualTo(ShiroUtils.getShiroUserId()).andSourceEqualTo(PlatformEnum.wapp.getCode());
//        userLoginMapper.updateByExampleSelective(userLogin, example);
    }

    private String encrypt(String password, SmsTypeEnum code) {
        User user = getUser();
        switch (code) {
            case UpdateLoginPwd:
                return PasswordUtil.encrypt(password, user.getSalt());
            case UpdatePayPwd:
                return PasswordUtil.encrypt(password, user.getPaySalt());
            default:
                return null;
        }
    }

    private User getUser() {

        return userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
    }

    @Override
    public LoginInfoVo login(LoginDto loginDto) {
        User user = userCustomMapper.getUserByUid(loginDto.getUid());
        A.check(user == null, "当前用户不存在");
        A.check(!user.getPassword().equals(PasswordUtil.encrypt(loginDto.getPassword(), user.getSalt())), "密码错误");

        String token = TokenUtils.generateValue();
        Date tokenExpireTime = new Date(DateUtils.getCurentTime().getTime() + TOKENEXPIRETIME * 3600 * 1000);
        UserLogin userLogin = userLoginCumstomMapper.getUserLoginByUserAndSource(user.getId(), PlatformEnum.wapp.getCode());
        if (userLogin == null) {
            userLogin = new UserLogin();
            userLogin.setId(UUIDUtils.getUUID()).setUserId(user.getId());
            userLogin.setToken(token).setExpireTime(tokenExpireTime);
            userLogin.setSource(PlatformEnum.wapp.getCode()).setLoginTime(DateUtils.getCurentTime());
            userLoginMapper.insertSelective(userLogin);
        } else {
            //清除之前登录的token
            iStringRedisService.delete(RedisConstant.app_token + userLogin.getToken());
            userLogin.setToken(token).setExpireTime(tokenExpireTime).setLoginTime(DateUtils.getCurentTime()).setLoginCnt(userLogin.getLoginCnt() + 1);
            userLoginMapper.updateByPrimaryKeySelective(userLogin);
        }

        iStringRedisService.set(RedisConstant.wapp_token + token, GsonUtils.getGson().toJson(ShiroUserPo.getInstance(user, userLogin)), TOKENEXPIRETIME, TimeUnit.HOURS);

        return LoginInfoVo.getInstance(user, userLogin);
    }

    @Override
    public void yinshangapply() {
        String userId = ShiroUtils.getShiroUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(user.getRole() != UserRoleEnum.ordinaryUser.getCode(), "只有普通用户才能申请银商");

        YinshangApplyExample example = new YinshangApplyExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
        A.check(!CollectionUtils.isEmpty(yinshangApplyMapper.selectByExample(example)), "您已经提交过银商申请");

        //判断用户是否存在未付款、已付款、申诉中的订单
        int unpaidCount = orderCustomMapper.countUnpaidByUserId(userId);
        A.check(unpaidCount > 0, "您存在未付款订单，请先处理");

        //查询户钱包信息
        WalletInfo walletInfo = walletInfoCustomMapper.getByUserIdAndType(userId, WalletTypeEnum.AEC.getCode());
        //查询最低保证金
        GlobalParameter globalParameter = iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MinBond.getCode());
        String bond = "";
        if (globalParameter == null) {
            bond = "100000";
        } else {
            bond = globalParameter.getCronValue();
        }
        A.check(!MathUtils.greatOrEqualForBg(walletInfo.getBalance(), new BigDecimal(bond)), "余额不足，无法开通银商权限，保证金最低限额" + bond);

        YinshangApply ys = new YinshangApply();
        ys.setId(UUIDUtils.getUUID()).setUserId(userId).setCreateTime(DateUtils.getCurentTime());
        yinshangApplyMapper.insertSelective(ys);
    }


    @Override
    public YsApplyVo yinshangstatus() {
        String userId = ShiroUtils.getShiroUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        MerchantApplyPo po = merchantApplyMapper.getYsApplyByUserId(userId);
        return YsApplyVo.getInstance(user, po);
    }

    @Override
    public boolean isSetPayPass() {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        if (StringUtils.isNotBlank(user.getPayPassword()) && StringUtils.isNotBlank(user.getPaySalt())) {
            return true;
        }
        return false;
    }

    @Override
    public void forgetPassword(ForgetPasswordDto forgetPasswordDto) {
        User user = userCustomMapper.getUserByUid(forgetPasswordDto.getUid());
        A.check(Objects.isNull(user), "用户不存在!");
        A.check(StringUtils.isBlank(forgetPasswordDto.getPhone()), "手机号不能为空");
        A.check(!StringUtils.equals(forgetPasswordDto.getPhone(), user.getPhone()), "手机号码填写有误!");

        smsService.checkSmsCode(user.getPhone(), SmsTypeEnum.ForgetLoginPassword.getCode(), forgetPasswordDto.getCaptchaCode());

        String salt = PasswordUtil.getSalt();
        user.setPassword(PasswordUtil.encrypt(forgetPasswordDto.getPassword(), salt)).setSalt(salt);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void changePhone(String nationalCode, String phone) {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        user.setNationalCode(nationalCode).setPhone(phone);
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        A.check(updateCount != 1, "更换手机号失败,请重试!");
        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        shiroUserPo.setPhone(phone);
        iStringRedisService.set(RedisConstant.app_token + shiroUserPo.getToken(), GsonUtils.getGson().toJson(shiroUserPo), TOKENEXPIRETIME, TimeUnit.HOURS);
    }

    @Override
    public PayVo isSetPay() {

        String userId = ShiroUtils.getShiroUserId();
        YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);

        boolean isPay = false;
        if (Objects.nonNull(yinshangIsPay)) {

            isPay = isPay(
                    yinshangIsPay.getCny(),
                    yinshangIsPay.getUsd(),
                    yinshangIsPay.getEur(),
                    yinshangIsPay.getHkd(),
                    yinshangIsPay.getAlipay(),
                    yinshangIsPay.getPaypal(),
                    yinshangIsPay.getXilian(),
                    yinshangIsPay.getSwift()
            );
        }

        return PayVo.builder().pay(isPay).name(userMapper.selectByPrimaryKey(userId).getName()).build();
    }

    private static boolean isPay(Integer... args) {

        for (Integer i : args) {
            if (Objects.nonNull(i) && i == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public AppVersion getAppBySourceAndOsType(UpdateAppDto updateAppDto) {
        A.check(StringUtils.isBlank(updateAppDto.getSource()), "平台类型不能为空");
        A.check(StringUtils.isBlank(updateAppDto.getOsType()), "操作系统类型不能为空");
        AppVersion appVersion = appVersionCustomMapper.getAppBySourceAndOsType(updateAppDto.getSource(), updateAppDto.getOsType());
        A.check(Objects.isNull(appVersion), "获取最新版本失败，请重试！");
        return appVersion;
    }
}
