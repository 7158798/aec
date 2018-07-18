package com.hengxunda.web.service;

import com.hengxunda.web.vo.SystemConfigVo;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
public interface ISystemInfoService {

    SystemConfigVo getSystemInfo();

    int updateSystemConfig(int generationRewardSwitch, int levelRewardSwitch, int withdrawSwitch) throws RuntimeException;
}
