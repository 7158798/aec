package com.hengxunda.wapp.service.impl;

import com.hengxunda.dao.entity.AppealType;
import com.hengxunda.dao.entity.AppealTypeExample;
import com.hengxunda.dao.mapper.AppealTypeMapper;
import com.hengxunda.wapp.service.IAppealTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppealTypeServiceImpl implements IAppealTypeService {
    @Autowired
    private AppealTypeMapper appealTypeMapper;

    @Override
    public List<AppealType> getAppealTypes() {
        AppealTypeExample e = new AppealTypeExample();
        return appealTypeMapper.selectByExample(e);
    }
}
