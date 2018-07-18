package com.hengxunda.app.service.impl;

import com.hengxunda.app.dto.CardStatusDto;
import com.hengxunda.app.dto.UserReceiveDto;
import com.hengxunda.app.oauth2.ShiroUtils;
import com.hengxunda.app.service.IUserReceiveService;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.UserReceiveMapper;
import com.hengxunda.dao.mapper.YinshangIsPayMapper;
import com.hengxunda.dao.mapper_custom.YinshangIsPayCustomMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserReceiveServiceImpl implements IUserReceiveService {

    @Autowired
    UserReceiveMapper userReceiveMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    YinshangIsPayMapper yinshangIsPayMapper;

    @Autowired
    YinshangIsPayCustomMapper yinshangIsPayCustomMapper;

    private UserReceive convert(UserReceiveDto userReceiveDto) {
        Date now = DateUtils.getCurentTime();
        String userId = ShiroUtils.getShiroUserId();
        UserReceive userReceive = new UserReceive()
                .setId(UUIDUtils.getUUID())
                .setName(userReceiveDto.getName())
                .setNo(userReceiveDto.getNo())
                .setAddress(userReceiveDto.getAddress())
                .setUserId(userId)
                .setUpdateUser(userId)
                .setRemark(userReceiveDto.getRemark())
                .setCreateTime(now).setUpdateTime(now);
        return userReceive;
    }

    @Override
    @Transactional
    public void insert(UserReceiveDto userReceiveDto) {
        check(userReceiveDto);
        userReceiveMapper.insertSelective(convert(userReceiveDto));

        String userId = ShiroUtils.getShiroUserId();
        YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);
        if (Objects.isNull(yinshangIsPay)) {
            YinshangIsPay addYinshangIsPay = new YinshangIsPay();
            addYinshangIsPay.setAlipay(0)
                    .setId(UUIDUtils.getUUID())
                    .setCreateTime(DateUtils.getCurentTime())
                    .setUserId(userId);
            yinshangIsPayMapper.insertSelective(addYinshangIsPay);

        } else {
            yinshangIsPay.setUpateUser(userId)
                    .setAlipay(0)
                    .setUpdateTime((DateUtils.getCurentTime()));
            yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
        }

    }

    @Override
    @Transactional
    public void updateStatus(CardStatusDto cardStatusDto) {
        String userId = ShiroUtils.getShiroUserId();
        userReceiveMapper.updateByPrimaryKeySelective(new UserReceive().setId(cardStatusDto.getId())
                .setStatus(cardStatusDto.getStatus())
                .setUpdateTime(DateUtils.getCurentTime()).setUpdateUser(userId));
        YinshangIsPay yinshangIsPay = yinshangIsPayCustomMapper.selectByUserId(userId);
        //打开支付宝
        if (cardStatusDto.getStatus()==0){
            if(yinshangIsPay.getAlipay()==0){
                return;
            }
            yinshangIsPay.setAlipay(0);
        }else{
            UserReceiveExample userReceive = new UserReceiveExample();
            userReceive.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
            List<UserReceive> userReceives = userReceiveMapper.selectByExample(userReceive);
            if(CollectionUtils.isNotEmpty(userReceives)){
                return;
            }
            yinshangIsPay.setAlipay(1);
        }
        yinshangIsPay.setUpateUser(userId)
                .setUpdateTime((DateUtils.getCurentTime()));
        yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
    }

    @Override
    public List<UserReceive> findList() {
        UserReceiveExample example = new UserReceiveExample();
        example.createCriteria().andUserIdEqualTo(ShiroUtils.getShiroUserId());

        return userReceiveMapper.selectByExample(example);
    }

    private void check(UserReceiveDto userReceiveDto) {
        User user = userMapper.selectByPrimaryKey(ShiroUtils.getShiroUserId());
        A.check(StringUtils.isBlank(user.getPayPassword()) || StringUtils.isBlank(user.getPaySalt()), "请先设置资金密码!");
        A.check(!PasswordUtil.check(userReceiveDto.getPayPass(), user.getPayPassword(), user.getPaySalt()), "资金密码验证错误！");

        UserReceiveExample userReceiveExample = new UserReceiveExample();
        userReceiveExample.createCriteria().andNoEqualTo(userReceiveDto.getNo());
        List<UserReceive> userReceives = userReceiveMapper.selectByExample(userReceiveExample);
        A.check(!CollectionUtils.sizeIsEmpty(userReceives), "支付宝号码已存在！");
    }


}
