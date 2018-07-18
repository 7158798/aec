package com.hengxunda.web.service.impl;

import com.hengxunda.common.Common.WebErrorConstant;
import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.dao.mapper_custom.GlobalParameterCustomMapper;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.web.service.ISystemInfoService;
import com.hengxunda.web.vo.SystemConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lsl
 * @Date: create in 2018/6/12
 */
@Slf4j
@Service
public class SystemInfoServiceImpl implements ISystemInfoService {
    @Autowired
    private IGlobalParameterService iGlobalParameterService;
    @Autowired
    private GlobalParameterCustomMapper globalParameterCustomMapper;
    @Override
    public SystemConfigVo getSystemInfo() {
        SystemConfigVo systemConfigVo = new SystemConfigVo();

        int generationRewardSwitch = Integer.parseInt(iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.GenerationRewardSwitch.getCode()).getCronValue());
        systemConfigVo.setGenerationRewardSwitch(generationRewardSwitch);

        int levelRewardSwitch = Integer.parseInt(iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.LevelRewardSwitch.getCode()).getCronValue());
        systemConfigVo.setLevelRewardSwitch(levelRewardSwitch);

        int withdrawSwitch = Integer.parseInt(iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.WithdrawSwitch.getCode()).getCronValue());
        systemConfigVo.setWithdrawSwitch(withdrawSwitch);

        return systemConfigVo;
    }

    @Override
    public int updateSystemConfig(int generationRewardSwitch, int levelRewardSwitch, int withdrawSwitch) throws RuntimeException {
        SystemConfigVo systemConfigVo = getSystemInfo();
        int result = 1;

        if(systemConfigVo.getGenerationRewardSwitch() != generationRewardSwitch){

            result = globalParameterCustomMapper.updateCronValueByCronKey(GlobalParameterEnum.GenerationRewardSwitch.getCode(), String.valueOf(generationRewardSwitch));
            if(result != 1){
                log.info(WebErrorConstant.GenerationRewardSwitchError);
                throw new RuntimeException(WebErrorConstant.GenerationRewardSwitchError);
            }

        }

        if(systemConfigVo.getLevelRewardSwitch() != levelRewardSwitch){

            result = globalParameterCustomMapper.updateCronValueByCronKey(GlobalParameterEnum.LevelRewardSwitch.getCode(), String.valueOf(levelRewardSwitch));
            if(result != 1){
                log.info(WebErrorConstant.LevelRewardSwitchError);
                throw new RuntimeException(WebErrorConstant.LevelRewardSwitchError);
            }
        }

        if (systemConfigVo.getWithdrawSwitch() != withdrawSwitch){

            result = globalParameterCustomMapper.updateCronValueByCronKey(GlobalParameterEnum.WithdrawSwitch.getCode(), String.valueOf(withdrawSwitch));
            if(result != 1){
                log.info(WebErrorConstant.WithdrawSwitchError);
                throw new RuntimeException(WebErrorConstant.WithdrawSwitchError);
            }
        }


        return result;
    }
}
