package com.hengxunda.app.service.impl;

import com.hengxunda.app.service.IOssService;
import com.hengxunda.app.vo.OssInfoVo;
import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.GlobalParameterExample;
import com.hengxunda.dao.mapper.GlobalParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OssServiceImpl implements IOssService {

    @Autowired
    private GlobalParameterMapper globalParameterMapper;

    /**
     * 获取OSS信息
     * @return
     */
    @Override
    public OssInfoVo ossInfo() {
        GlobalParameterExample globalParamerExample = new GlobalParameterExample();
        globalParamerExample.createCriteria().andGroupNameEqualTo(GlobalParameterEnum.ALIYUN_ACCESS_KEY_ID.getGroup());
        List<GlobalParameter> globalParameters = globalParameterMapper.selectByExample(globalParamerExample);
        OssInfoVo ossInfoVo = new OssInfoVo();
        globalParameters.stream().forEach(globalParameter -> {
            String cronValue = globalParameter.getCronValue();
            if (globalParameter.getCronKey().equals(GlobalParameterEnum.ALIYUN_ENDPOINT.getCode())) {
                ossInfoVo.setAliyunEndPoint(cronValue);
            } else if (globalParameter.getCronKey().equals(GlobalParameterEnum.ALIYUN_BUKETNAME.getCode())) {
                ossInfoVo.setAliyunBuketName(cronValue);
            } else if (globalParameter.getCronKey().equals(GlobalParameterEnum.ALIYUN_ACCESS_KEY_ID.getCode())) {
                ossInfoVo.setAliyunAccessKeyID(cronValue);
            } else if (globalParameter.getCronKey().equals(GlobalParameterEnum.ALIYUN_ACCESS_KEY_SECRET.getCode())) {
                ossInfoVo.setAliyunAccessKeySecret(cronValue);
            }
        });
        return ossInfoVo;
    }
}
