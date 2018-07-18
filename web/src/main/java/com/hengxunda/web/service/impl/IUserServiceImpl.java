package com.hengxunda.web.service.impl;

import com.hengxunda.common.Common.RedisConstant;
import com.hengxunda.common.Common.UserRoleConstant;
import com.hengxunda.common.Enum.PlatformEnum;
import com.hengxunda.common.Enum.UserStatusEnum;
import com.hengxunda.common.exception.ServiceException;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.UserBankInfoMapper;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.UserReceiveMapper;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper_custom.UserCustomMapper;
import com.hengxunda.dao.mapper_custom.UserLoginCumstomMapper;
import com.hengxunda.dao.mapper_custom.UserRecommendCustomMapper;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.dao.po.UserPo;
import com.hengxunda.dao.po.web.LockUpPo;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.web.service.IUserService;
import com.hengxunda.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Date: create in 2018/6/6
 */
@Slf4j
@Service
public class IUserServiceImpl implements IUserService {

    @Value("${token.userExpireTime}")
    private int USERTOKENEXPIRETIME;

    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WalletInfoMapper walletInfoMapper;
    @Autowired
    private UserBankInfoMapper userBankInfoMapper;
    @Autowired
    private UserReceiveMapper userReceiveMapper;
    @Autowired
    private UserRecommendCustomMapper userRecommendCustomMapper;
    @Autowired
    private IStringRedisService iStringRedisService;
    @Autowired
    private UserLoginCumstomMapper userLoginCumstomMapper;

    @Override
    public TopologyVo topology(String id) {

        return getTopo(id,1);
    }

    @Override
    public List<TopologyVo> getTopologys() {

        List<String> list = userRecommendCustomMapper.selectRootUserId();

        List<TopologyVo> topologyVos = new ArrayList<>();

        for (String id:list){
            topologyVos.add(getTopo(id,1));
        }

        return topologyVos;
    }

    @Override
    public int setToShop(String id) {
        return userCustomMapper.setUserToShop(id);
    }

    @Override
    public int setToUser(String id) {
        return userCustomMapper.setShopToUser(id);
    }

    @Override
    public LockUpListVo getLockUpByUserId(String id, Page page) {
        List<LockUpPo> lockUpPos = userCustomMapper.getUserLockInfo(id,new Page(page.getPageNo(),page.getLimit()));
        int count = userCustomMapper.countUserLockInfo(id);

        LockUpListVo lockUpListVo = new LockUpListVo();
        lockUpListVo.setLockUpPos(lockUpPos).setTotal(count);

        return lockUpListVo;
    }

    @Override
    public List<UserVo> downLoadExcel(String uid, String mobile, String name, String nickName, int level, Date beginTime, Date endTime, HttpServletResponse response) {

        //获取数据
        UserPo userPo = new UserPo();
        //userPo.setRole(UserRoleConstant.USER_ROLE_CUSTOMER);
        userPo.setCreateBeginTime(beginTime).setCreateEndTime(endTime);
        if(uid!=null&&!uid.equals("")){
            userPo.setUid(uid);
        }
        if(mobile!=null&&!mobile.equals("")){
            userPo.setPhone(mobile);
        }
        if(name!=null&&!name.equals("")){
            userPo.setName(name);
        }
        if(nickName!=null&&!nickName.equals("")){
            userPo.setNickName(nickName);
        }

        if(level!= 100){
            userPo.setLevel(level);
        }
        List<UserPo> userPos = userCustomMapper.getUsersForDownload(userPo);

        UserListVo userListVo = UserListVo.format(userPos);

        return userListVo.getUsers();

        //输出到前端
//        downloadForUser(response,userPos);
    }

    @Override
    @Transactional
    public int FrozenUsers(List<String> list) {

        //清除Redis中的登录信息
        for (String id: list){
            User user = userMapper.selectByPrimaryKey(id);
            List<UserLogin> userLogins = userLoginCumstomMapper.getUserLoginByUserId(user.getId());
            for (UserLogin userLogin:userLogins) {
                String redisKey = "";
                if(userLogin.getSource() == PlatformEnum.app.getCode()){
                    redisKey = RedisConstant.app_token;
                }else if(userLogin.getSource() == PlatformEnum.wapp.getCode()){
                    redisKey = RedisConstant.wapp_token;
                }
                String _token = userLogin.getToken();
                String json = iStringRedisService.get(RedisConstant.app_token +_token );

                if (StringUtils.isNotBlank(json)){
                    ShiroUserPo shiroUserPo = GsonUtils.getGson().fromJson(json, ShiroUserPo.class);
                    shiroUserPo.setStatus(UserStatusEnum.Forzen.getCode());
                    iStringRedisService.set(redisKey+_token,GsonUtils.getGson().toJson(ShiroUserPo.getInstance(user,userLogin)),USERTOKENEXPIRETIME, TimeUnit.HOURS);
                    log.info("更改redis中用户：{}状态",id);
                }
            }

        }

        return userCustomMapper.frozenUserById(list);
    }

    @Override
    @Transactional
    public int UnFrozenUsers(List<String> list) {
        for (String id: list){
            User user = userMapper.selectByPrimaryKey(id);
            List<UserLogin> userLogins = userLoginCumstomMapper.getUserLoginByUserId(user.getId());
            for (UserLogin userLogin:userLogins) {
                String redisKey = "";
                if(userLogin.getSource() == PlatformEnum.app.getCode()){
                    redisKey = RedisConstant.app_token;
                }else if(userLogin.getSource() == PlatformEnum.wapp.getCode()){
                    redisKey = RedisConstant.wapp_token;
                }
                String _token = userLogin.getToken();
                String json = iStringRedisService.get(RedisConstant.app_token +_token );

                if (StringUtils.isNotBlank(json)){
                    ShiroUserPo shiroUserPo = GsonUtils.getGson().fromJson(json, ShiroUserPo.class);
                    shiroUserPo.setStatus(UserStatusEnum.Effective.getCode());
                    iStringRedisService.set(redisKey+_token,GsonUtils.getGson().toJson(ShiroUserPo.getInstance(user,userLogin)),USERTOKENEXPIRETIME, TimeUnit.HOURS);
                    log.info("更改redis中用户：{}状态",id);
                }
            }

        }
        return userCustomMapper.UnfrozenUserById(list);
    }

    @Override
    public UserListVo getUsers(String uid, String mobile, String name, String nickName, String creatTime, int level, Page page) {

        UserPo userPo = new UserPo();
        //userPo.setRole(UserRoleConstant.USER_ROLE_CUSTOMER);
        if(uid!=null&&!uid.equals("")){
            userPo.setUid(uid);
        }
        if(mobile!=null&&!mobile.equals("")){
            userPo.setPhone(mobile);
        }
        if(name!=null&&!name.equals("")){
            userPo.setName(name);
        }
        if(nickName!=null&&!nickName.equals("")){
            userPo.setNickName(nickName);
        }
        if(creatTime!=null&&!creatTime.equals("")){
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begin = sd.parse(creatTime);
                Date end = sd.parse(creatTime.replace("00:00:00","23:59:59"));
                userPo.setCreateBeginTime(begin).setCreateEndTime(end);
            } catch (ParseException e) {
                log.info("时间格式化失败");
            }
        }
        if(level!= 100){
            userPo.setLevel(level);
        }

        List<UserPo> list = userCustomMapper.getUsersSelect(userPo,new Page(page.getPageNo(), page.getLimit()));
        int count = userCustomMapper.countUsersSelect(userPo);
        UserListVo userListVo = UserListVo.format(list);
        userListVo.setTotal(count);
        return userListVo;
    }

    @Override
    @Transactional
    public UserDetailVo getUserById(String id) {

        UserDetailVo userDetailVo = new UserDetailVo();

        //用户基本信息
        User user = userMapper.selectByPrimaryKey(id);
        userDetailVo.setUserVo(UserVo.format(user));

        //获取钱包信息
        WalletInfoExample walletInfoExample = new WalletInfoExample();
        walletInfoExample.or().andUserIdEqualTo(id);
        List<WalletInfo> walletInfos = walletInfoMapper.selectByExample(walletInfoExample);
        userDetailVo.setWalletInfoVos(WalletInfoVo.formatList(walletInfos));

        //支付方式信息
        UserBankInfoExample userBankInfoExample = new UserBankInfoExample();
        userBankInfoExample.or().andUserIdEqualTo(id);
        List<UserBankInfo> userBankInfos = userBankInfoMapper.selectByExample(userBankInfoExample);
        List<BankInfoVo> bankInfoVos = BankInfoVo.formatBankList(userBankInfos);

        UserReceiveExample userReceiveExample  = new UserReceiveExample();
        userReceiveExample.or().andUserIdEqualTo(id);
        List<UserReceive> userReceives = userReceiveMapper.selectByExample(userReceiveExample);
        List<BankInfoVo> bankInfoVos1 = BankInfoVo.formatReceiveList(userReceives);

        bankInfoVos.addAll(bankInfoVos1);
        userDetailVo.setBankInfoVos(bankInfoVos);


        return userDetailVo;
    }

    /*private void downloadForUser(HttpServletResponse response, List<UserPo> userPos){

        try {
            String title = "User Info";
            String[] rowsName = new String[]{"序号","手机号","姓名","昵称","注册时间","会员级别","推荐人手机"};
            List<Object[]>  dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (int i = 0; i < userPos.size(); i++) {
                UserPo userPo = userPos.get(i);
                objs = new Object[rowsName.length];
                objs[0] = i;
                objs[1] = userPo.getPhone();
                objs[2] = userPo.getName();
                objs[3] = userPo.getNickName();
                objs[4] = DateUtils.format(userPo.getCreateTime());
                objs[5] = String.valueOf(userPo.getLevel());
                objs[6] = userPo.getRecommendPhone();
                dataList.add(objs);
            }

            ExcelHelper eh = new ExcelHelper(title, rowsName, dataList, response);
            eh.export();

        }catch (Exception e){
            log.error("用户信息导出失败");
        }


        OutputStream os = ExcelHelper.getOS(response,"用户信息");

        try {
            WritableWorkbook book = Workbook.createWorkbook(os);
            WritableSheet sheet = book.createSheet("用户信息表", 0);
            sheet.addCell(new jxl.write.Label(0, 0, "序号"));
            sheet.addCell(new jxl.write.Label(1, 0, "手机号"));
            sheet.addCell(new jxl.write.Label(2, 0, "姓名"));
            sheet.addCell(new jxl.write.Label(3, 0, "昵称"));
            sheet.addCell(new jxl.write.Label(4, 0, "注册时间"));
            sheet.addCell(new jxl.write.Label(5, 0, "会员级别"));
            sheet.addCell(new jxl.write.Label(6, 0, "推荐人手机"));
            for (int i = 1; i < userPos.size() + 1; i++) {
                sheet.addCell(new jxl.write.Label(0, i, String.valueOf(i)));// 序号从1开始
                sheet.addCell(new jxl.write.Label(1, i, userPos.get(i - 1).getPhone()));
                sheet.addCell(new jxl.write.Label(2, i, userPos.get(i - 1).getName()));
                sheet.addCell(new jxl.write.Label(3, i, userPos.get(i - 1).getNickName()));
                sheet.addCell(new jxl.write.Label(4, i, DateUtils.format(userPos.get(i - 1).getCreateTime())));
                sheet.addCell(new jxl.write.Label(5, i,  String.valueOf(userPos.get(i - 1).getRole())));
                sheet.addCell(new jxl.write.Label(6, i,  userPos.get(i - 1).getRecommendPhone()));
            }
            book.write();
            book.close();
        } catch (Exception e) {
            log.error("导出excel出现异常", e);
            throw new ServiceException("导出失败", e);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("关流出现异常", e);
                    e.printStackTrace();
                }
            }
        }
    }*/

    //使用递归
    private TopologyVo getTopo(String id, int index){
        TopologyVo topologyVo = new TopologyVo();
        User user = userMapper.selectByPrimaryKey(id);
        topologyVo.setIndex(index).setId(id).setLabel(user.getPhone()+"_"+user.getUid()+"_"+user.getLevel()).setUserId(user.getUid()).setPhone(user.getPhone())
                .setName(user.getName()).setNickName(user.getNickName()).setLevel(user.getLevel()).setCreateTime(user.getCreateTime()).setRole(user.getRole())
                .setFullAchievement(user.getFullAchievement()).setQualifiedNum(user.getQualifiedNum()).setSelfAchievement(user.getSelfAchievement());
        List<UserRecommend> recommends = userRecommendCustomMapper.selectRefereeIdByRecommendId(id);
        index = index+1;
        if(recommends.size() != 0){
            List<TopologyVo> list = new ArrayList<>();
            TopologyVo topologyVo1;
            for(int i = 0;i<recommends.size();i++){
                topologyVo1 = getTopo(recommends.get(i).getRefereeId(),index);
                list.add(topologyVo1);
            }
            topologyVo.setChildren(list);
        }

        return topologyVo;
    }
}
