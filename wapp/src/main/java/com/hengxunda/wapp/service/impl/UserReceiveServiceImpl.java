package com.hengxunda.wapp.service.impl;

import com.hengxunda.common.exception.BusinessException;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserReceive;
import com.hengxunda.dao.entity.UserReceiveExample;
import com.hengxunda.dao.entity.YinshangIsPay;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.UserReceiveMapper;
import com.hengxunda.dao.mapper.YinshangIsPayMapper;
import com.hengxunda.dao.mapper_custom.YinshangIsPayCustomMapper;
import com.hengxunda.wapp.dto.CardStatusDto;
import com.hengxunda.wapp.dto.UserReceiveDto;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.IUserReceiveService;
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
                .setRemark(userReceiveDto.getRemark())
                .setUpdateUser(userId)
                .setCreateTime(now).setUpdateTime(now);
        return userReceive;
    }

    @Override
    @Transactional
    public void insert(UserReceiveDto userReceiveDto) {
        try {
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
                yinshangIsPay.setAlipay(0)
                        .setUpateUser(userId)
                        .setUpdateTime((DateUtils.getCurentTime()));
                yinshangIsPayMapper.updateByPrimaryKeySelective(yinshangIsPay);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void update(CardStatusDto cardStatusDto) {
        try {
            String userId = ShiroUtils.getShiroUserId();
            userReceiveMapper.updateByPrimaryKeySelective(new UserReceive()
                    .setId(cardStatusDto.getId())
                    .setStatus(cardStatusDto.getStatus())
                    .setUpdateTime(DateUtils.getCurentTime())
                    .setUpdateUser(ShiroUtils.getShiroUserId()));
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
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
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
        A.check(!PasswordUtil.check(userReceiveDto.getPayPass(), user.getPayPassword(), user.getPaySalt()), "资金密码验证错误!");

        UserReceiveExample example = new UserReceiveExample();
        example.createCriteria().andNoEqualTo(userReceiveDto.getNo());
        A.check(!CollectionUtils.sizeIsEmpty(userReceiveMapper.selectByExample(example)), "支付宝账号已存在!");
    }
}
