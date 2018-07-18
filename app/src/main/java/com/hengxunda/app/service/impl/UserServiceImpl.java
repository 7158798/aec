package com.hengxunda.app.service.impl;

import com.hengxunda.app.dto.LoginDto;
import com.hengxunda.app.dto.RegisterDto;
import com.hengxunda.app.dto.UpdateAppDto;
import com.hengxunda.app.dto.UpdateLoginPasswordDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IUserService;
import com.hengxunda.app.vo.MyInfoVo;
import com.hengxunda.app.vo.UserInfoVo;
import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Enum.*;
import com.hengxunda.common.utils.*;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.*;
import com.hengxunda.dao.mapper_custom.*;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.generalservice.service.ISmsService;
import com.hengxunda.generalservice.service.IStringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Value("${token.expireTime}")
    private int TOKENEXPIRETIME;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRecommendMapper userRecommendMapper;

    @Autowired
    private NoticeCustomMapper noticeCustomMapper;

    @Autowired
    private UserCustomMapper userCustomapper;

//    @Autowired
//    private WalletAsync walletAsync;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserLoginCumstomMapper userLoginCumstomMapper;

    @Autowired
    private OrderCustomMapper orderCustomMapper;

    @Autowired
    private OrderAppealMapper orderAppealMapper;

    @Autowired
    private OrderAppealCustomMapper orderAppealCustomMapper;

    @Autowired
    private IStringRedisService iStringRedisService;

    @Autowired
    private ISmsService iSmsService;

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private WalletInfoCustomMapper walletInfoCustomMapper;

    @Autowired
    private AppVersionCustomMapper appVersionCustomMapper;

    @Override
    public User getUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userCustomapper.getUserByPhone(phone);
    }

    @Override
    public User getUserByUid(String uid) {
        return userCustomapper.getUserByUid(uid);
    }

    /**
     * 操作步骤
     * 1. 保存用户信息
     * 2. 保存推荐关系
     * 3. 绑定用户钱包地址
     */
    @Transactional
    @Override
    public void register(RegisterDto registerDto, User refereeUser) {

        //校验uid是否存在
        User uidUser = userCustomapper.getUserByUid(registerDto.getUid());
        A.check(uidUser != null, StatusCodeEnum.Registered.getCode(), StatusCodeEnum.Registered.getValue());

        Date currentTime = DateUtils.getCurentTime();
        String salt = PasswordUtil.getSalt();
        //1.保存用户信息
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.setId(UUIDUtils.getUUID()).setCreateTime(currentTime);
        user.setPassword(PasswordUtil.encrypt(registerDto.getPassword(), salt));
        user.setSalt(salt);
        String nationalCode = StringUtils.isBlank(registerDto.getNationalCode()) ? "86" : registerDto.getNationalCode();
        user.setNationalCode(nationalCode);
        userMapper.insertSelective(user);
        log.info("==>register userinfo<==");

        //2.保存推荐关系
        if (refereeUser != null) {
            UserRecommend ur = new UserRecommend();
            ur.setId(UUIDUtils.getUUID());
            ur.setRecommendId(refereeUser.getId()); //推荐人
            ur.setRefereeId(user.getId());          //被推荐人
            ur.setCreateTime(currentTime);
            userRecommendMapper.insertSelective(ur);
        }

        //3.绑定用户钱包地址
        walletInfoCustomMapper.bindWalletInfoAddress(user.getId());

//        3.初始化钱包信息（异步）
//        walletAsync.BTCWalletAddress(currentTime, user);
//        walletAsync.LTCWalletAddress(currentTime, user);
//        walletAsync.AECAndMSCWalletAddress(currentTime, user);


    }


    @Override
    public UserInfoVo login(LoginDto loginDto) {

        User user = userCustomapper.getUserByUid(loginDto.getUid());
        A.check(user == null, "当前用户不存在");
        A.check(!user.getPassword().equals(PasswordUtil.encrypt(loginDto.getPassword(), user.getSalt())), "密码错误");
        A.check(user.getStatus() == UserStatusEnum.Forzen.getCode(), StatusCodeEnum.CurrentUserFrozen.getCode(), StatusCodeEnum.CurrentUserFrozen.getValue());

        List<WalletInfo> walletInfos = walletInfoCustomMapper.getByUserId(user.getId());

        String token = TokenUtils.generateValue();
        Date tokenExpireTime = new Date(DateUtils.getCurentTime().getTime() + TOKENEXPIRETIME * 3600 * 1000);
        UserLogin userLogin = userLoginCumstomMapper.getUserLoginByUserAndSource(user.getId(), PlatformEnum.app.getCode());
        if (userLogin == null) {
            userLogin = new UserLogin();
            userLogin.setId(UUIDUtils.getUUID()).setUserId(user.getId());
            userLogin.setToken(token).setExpireTime(tokenExpireTime);
            userLogin.setSource(PlatformEnum.app.getCode()).setLoginTime(DateUtils.getCurentTime());
            userLoginMapper.insertSelective(userLogin);
        } else {
            //清除之前登录的token
            iStringRedisService.delete(RedisConstant.app_token + userLogin.getToken());

            userLogin.setToken(token).setExpireTime(tokenExpireTime).setLoginTime(DateUtils.getCurentTime()).setLoginCnt(userLogin.getLoginCnt() + 1);
            userLoginMapper.updateByPrimaryKeySelective(userLogin);
        }
        iStringRedisService.set(RedisConstant.app_token + token, GsonUtils.getGson().toJson(ShiroUserPo.getInstance(user, userLogin)), TOKENEXPIRETIME, TimeUnit.HOURS);
        return UserInfoVo.getInstance(user, userLogin, walletInfos);
    }

    @Override
    public MyInfoVo getById() {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        A.check(Objects.isNull(user), "用户不存在!");

        //订单总数
        int orderCount = orderCustomMapper.count(new Order().setUserId(user.getId()).setStatus(OrderStatusEnum.completed.getCode()));

        //申诉次数
        OrderAppealExample orderAppealExample = new OrderAppealExample();
        orderAppealExample.createCriteria().andUserIdEqualTo(user.getId());
        int appealCount = orderAppealMapper.countByExample(orderAppealExample);

        //胜诉次数
        int appealWinCount = orderAppealCustomMapper.countWinAppeal(user.getId());

        //未读取记录条数
        int noReadNumber = noticeCustomMapper.getNoReadNumber(user.getId());

        return new MyInfoVo().setUid(user.getUid())
                .setNickName(user.getNickName())
                .setAvatar(user.getAvatar())
                .setTradingNumber(orderCount)
                .setAppealNumber(appealCount)
                .setWinNumber(appealWinCount)
                .setNoReadNumber(noReadNumber);
    }

    @Override
    public void updateNick(String nick) {
        String userId = ShiroUtils.getShiroUserId();
        User user = new User();
        user.setNickName(nick).setId(userId);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateName(String name) {
        String userId = ShiroUtils.getShiroUserId();
        User user = new User();
        user.setName(name).setId(userId);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void checkPassword(String password) {
        String userId = ShiroUtils.getShiroUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        A.check(!PasswordUtil.check(password, user.getPassword(), user.getSalt()), "登录密码验证错误!");
    }

    @Override
    public void logOut() {
        iStringRedisService.delete(RedisConstant.app_token + RequestUtils.getToken());
    }

    /**
     * 更换手机号
     *
     * @param phone
     */
    @Override
    public void changePhone(String nationalCode, String phone) {
        String userId = ShiroUtils.getShiroUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        user.setNationalCode(nationalCode);
        user.setPhone(phone);
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        A.check(updateCount != 1, "更换手机号失败,请重试!");
        ShiroUserPo shiroUserPo = ShiroUtils.getShiroUser();
        shiroUserPo.setPhone(phone);
        iStringRedisService.set(RedisConstant.app_token + shiroUserPo.getToken(), GsonUtils.getGson().toJson(shiroUserPo), TOKENEXPIRETIME, TimeUnit.HOURS);
    }

    /**
     * 忘记密码
     *
     * @param updateLoginPasswordDto
     */
    @Override
    public void forgetPassword(UpdateLoginPasswordDto updateLoginPasswordDto) {
        User user = userCustomapper.getUserByUid(updateLoginPasswordDto.getUid());
        A.check(Objects.isNull(user), "用户不存在!");
        A.check(StringUtils.isBlank(updateLoginPasswordDto.getPhone()), "手机号不能为空");
        A.check(!StringUtils.equals(updateLoginPasswordDto.getPhone(), user.getPhone()), "手机号码填写有误!");

        iSmsService.checkSmsCode(user.getPhone(), SmsTypeEnum.ForgetLoginPassword.getCode(), updateLoginPasswordDto.getCaptchCode());

        String salt = PasswordUtil.getSalt();
        user.setPassword(PasswordUtil.encrypt(updateLoginPasswordDto.getPassword(), salt));
        user.setSalt(salt);
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 查询app信息
     *
     * @return
     */
    @Override
    public List<AppVersion> appInfo() {
        AppVersionExample appVersionExample = new AppVersionExample();
        appVersionExample.createCriteria().andSourceEqualTo(PlatformEnum.wapp.getCode());
        return appVersionMapper.selectByExample(appVersionExample);
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
