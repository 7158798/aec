package com.hengxunda.generalservice.service;

import com.hengxunda.dao.entity.MscRecord;

/**
 *  一键换币触发 改价、级差、代数奖励
 */
public interface IAwardService {
    void syncExec(MscRecord mscRecord);
}
