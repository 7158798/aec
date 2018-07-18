package com.hengxunda.wapp.service.impl;

import com.hengxunda.common.Enum.OrderAppealStatusEnum;
import com.hengxunda.common.Enum.OrderStatusEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.DateUtils;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.AppealType;
import com.hengxunda.dao.entity.Order;
import com.hengxunda.dao.entity.OrderAppeal;
import com.hengxunda.dao.mapper.AppealTypeMapper;
import com.hengxunda.dao.mapper.OrderAppealMapper;
import com.hengxunda.dao.mapper.OrderMapper;
import com.hengxunda.dao.mapper_custom.OrderAppealCustomMapper;
import com.hengxunda.wapp.dto.AppealDto;
import com.hengxunda.wapp.oauth2.ShiroUtils;
import com.hengxunda.wapp.service.IAppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppealServiceImpl implements IAppealService {

    @Autowired
    private AppealTypeMapper  appealTypeMapper;

    @Autowired
    private OrderAppealMapper orderAppealMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderAppealCustomMapper orderAppealCustomMapper;

    @Override
    public void appeal(AppealDto dto) {
        Order order = orderMapper.selectByPrimaryKey(dto.getOrderId());
        A.check(order==null,"当前订单不存在");
        A.check(order.getStatus() != OrderStatusEnum.paid.getCode(),"不支持的订单状态");

        AppealType appealType = appealTypeMapper.selectByPrimaryKey(dto.getAppealTypeId());
        A.check(appealType==null,"申诉类型不存在");
        //检验是否已提交申诉
        int orderAppealcount = orderAppealCustomMapper.countOrderAppealByOrderIdAndUserId(order.getId(), ShiroUtils.getShiroUserId());
        A.check(orderAppealcount>0,"您的申诉正在处理中");

        Date currentTime = DateUtils.getCurentTime();

        order.setAppealStatus(OrderAppealStatusEnum.arbitrament.getCode());              //修改订单申诉状态
        order.setStatus(OrderStatusEnum.appealing.getCode()).setUpdateTime(currentTime); //修改订单状态为申诉
        order.setAppealUser(ShiroUtils.getShiroUserId());
        orderMapper.updateByPrimaryKeySelective(order);

        //保存订单申诉
        OrderAppeal orderAppeal = new OrderAppeal();
        orderAppeal.setId(UUIDUtils.getUUID()).setOrderId(order.getId()).setAppealId(appealType.getId()).setUserId(ShiroUtils.getShiroUserId());
        orderAppeal.setAppealContent(appealType.getDescri()).setContent(dto.getContent()).setImg(dto.getUrl()).setCreateTime(currentTime);
        orderAppealMapper.insertSelective(orderAppeal);
    }
}
