package com.hengxunda.wapp.service.impl;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.dao.entity.GlobalParameterExample;
import com.hengxunda.dao.mapper.GlobalParameterMapper;
import com.hengxunda.wapp.service.IOssService;
import com.hengxunda.wapp.vo.OssInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OssServiceImpl implements IOssService {

    @Autowired
    private GlobalParameterMapper globalParameterMapper;

    @Override
    public OssInfoVo ossInfo() {
        GlobalParameterExample globalParameterExample = new GlobalParameterExample();
        globalParameterExample.createCriteria().andGroupNameEqualTo(GlobalParameterEnum.ALIYUN_ACCESS_KEY_ID.getGroup());

        OssInfoVo ossInfoVo = new OssInfoVo();
        globalParameterMapper.selectByExample(globalParameterExample).stream().forEach(globalParameter -> {
            String cronValue = globalParameter.getCronValue();
            switch (GlobalParameterEnum.getByCode(globalParameter.getCronKey())) {
                case ALIYUN_ENDPOINT:
                    ossInfoVo.setAliyunEndPoint(cronValue);
                    break;
                case ALIYUN_BUKETNAME:
                    ossInfoVo.setAliyunBuketName(cronValue);
                    break;
                case ALIYUN_ACCESS_KEY_ID:
                    ossInfoVo.setAliyunAccessKeyID(cronValue);
                    break;
                case ALIYUN_ACCESS_KEY_SECRET:
                    ossInfoVo.setAliyunAccessKeySecret(cronValue);
                    break;
            }
        });

        return ossInfoVo;
    }
}
