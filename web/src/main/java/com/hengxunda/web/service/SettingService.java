package com.hengxunda.web.service;

import com.hengxunda.dao.entity.GenerationAwardParameter;
import com.hengxunda.dao.entity.LevelAwardParameter;
import com.hengxunda.dao.po.ShiroUserPo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 系統配置
 * @Author: QiuJY
 * @Date: Created in 16:33 2018/6/12
 */
public interface SettingService {

    void update(Map<String, String> map , ShiroUserPo admin);

    Map<String, String> get();

    List<GenerationAwardParameter> getGenerationAward();

    void updateGenerationAward(GenerationAwardParameter generationAwardParameter);

    List<LevelAwardParameter> getLevelAward();

    void updateLevelAward(LevelAwardParameter levelAwardParameter);
}
