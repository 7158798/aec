package com.hengxunda.web.service.impl;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.Enum.MscEffectStatusEnum;
import com.hengxunda.common.Enum.MscRecordTypeEnum;
import com.hengxunda.common.utils.A;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.common.utils.RegexUtil;
import com.hengxunda.dao.entity.*;
import com.hengxunda.dao.mapper.GenerationAwardParameterMapper;
import com.hengxunda.dao.mapper.GlobalParameterMapper;
import com.hengxunda.dao.mapper.LevelAwardParameterMapper;
import com.hengxunda.dao.mapper.MscRecordMapper;
import com.hengxunda.dao.po.ShiroUserPo;
import com.hengxunda.web.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Description:
 * 系統配置
 *
 * @Author: QiuJY
 * @Date: Created in 16:39 2018/6/12
 */
@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private GlobalParameterMapper globalParameterMapper;
    @Autowired
    private GenerationAwardParameterMapper generationAwardParameterMapper;
    @Autowired
    private LevelAwardParameterMapper levelAwardParameterMapper;
    @Autowired
    private MscRecordMapper mscRecordMapper;

    @Transactional
    @Override
    public void update(Map<String, String> map, ShiroUserPo admin) {
        if (map == null || map.keySet().size() == 0) {
            return;
        }
        List<GlobalParameter> entities = globalParameterMapper.selectByExample(new GlobalParameterExample());
        Map<String, GlobalParameter> entityMap = new HashMap<String, GlobalParameter>();
        if (entities.size() > 0) {
            for (GlobalParameter entity : entities) {
                entityMap.put(entity.getCronKey(), entity);
            }
        }
        //自动更新MSC/AEC价格
        if (map.get(GlobalParameterEnum.AEC2CNY.getCode()) != null || map.get(GlobalParameterEnum.MSC2CNY.getCode()) != null) {
            BigDecimal aec = map.get(GlobalParameterEnum.AEC2CNY.getCode()) == null ? new BigDecimal(entityMap.get(GlobalParameterEnum.AEC2CNY.getCode()).getCronValue()) : new BigDecimal(map.get(GlobalParameterEnum.AEC2CNY.getCode()));
            BigDecimal msc = map.get(GlobalParameterEnum.MSC2CNY.getCode()) == null ? new BigDecimal(entityMap.get(GlobalParameterEnum.MSC2CNY.getCode()).getCronValue()) : new BigDecimal(map.get(GlobalParameterEnum.MSC2CNY.getCode()));
            BigDecimal msc2aec = MathUtils.divide8p(msc, aec);
            map.put(GlobalParameterEnum.MSC2AEC.getCode(), msc2aec + "");
        }
        //  参数修改是否影响msc价格
        Boolean isEffectMscPrice = false;

        for (Map.Entry<String, String> item : map.entrySet()) {
            // 检查数据格式
            A.check(!RegexUtil.checkNumber(item.getValue()), "参数值必须为数字");
            A.check(MathUtils.lessForBg(new BigDecimal(item.getValue()), BigDecimal.ZERO), "参数值不能小于0");
            A.check(GlobalParameterEnum.getByCode(item.getKey()) == null, "参数不存在");

            //执行更新
            if (entityMap.get(item.getKey()) == null) {
                GlobalParameterEnum parameter = GlobalParameterEnum.getByCode(item.getKey());
                globalParameterMapper.insertSelective(new GlobalParameter(item.getKey(), item.getValue(), parameter.getGroup(), parameter.getValue(), admin.getId(), new Date()));
            } else {
                GlobalParameter parameterEntity = entityMap.get(item.getKey());
                parameterEntity.setCronValue(item.getValue());
                parameterEntity.setUpdateUser(admin.getId());
                parameterEntity.setUpdateTime(new Date());
                globalParameterMapper.updateByPrimaryKeySelective(parameterEntity);
                if (item.getKey().equals(GlobalParameterEnum.AEC2CNY.getCode()) ||
                        item.getKey().equals(GlobalParameterEnum.MSC2CNY.getCode()) ||
                        item.getKey().equals(GlobalParameterEnum.MscIncreaseSplit.getCode()) ||
                        item.getKey().equals(GlobalParameterEnum.MscIncreasePrice.getCode())) {
                    isEffectMscPrice = true;
                }
            }
        }

        if (isEffectMscPrice) {
            //  设置AEC购买MSC统计标识为无效
            MscRecord mscRecord = new MscRecord();
            mscRecord.setEffectStatus(MscEffectStatusEnum.invalid.getCode());
            mscRecord.setUpdateTime(new Date());
            mscRecord.setUpdateUser("backUser");
            MscRecordExample mscRecordExample = new MscRecordExample();
            mscRecordExample.createCriteria()
                    .andEffectStatusEqualTo(MscEffectStatusEnum.valid.getCode())
                    .andTypeEqualTo(MscRecordTypeEnum.bugMsc.getCode());
            mscRecordMapper.updateByExampleSelective(mscRecord, mscRecordExample);

            //  设置剩余不计入涨价区间的msc数量为0
            GlobalParameter updRestMscAmountParam = new GlobalParameter();
            updRestMscAmountParam.setCronValue("0.00");
            updRestMscAmountParam.setUpdateTime(new Date());
            updRestMscAmountParam.setUpdateUser("backUser");
            GlobalParameterExample restMscAmountExample = new GlobalParameterExample();
            restMscAmountExample.createCriteria().andCronKeyEqualTo(GlobalParameterEnum.RestMscAmount.getCode());
            globalParameterMapper.updateByExampleSelective(updRestMscAmountParam, restMscAmountExample);
        }
    }

    @Override
    public Map<String, String> get() {
        List<GlobalParameter> entities = globalParameterMapper.selectByExample(new GlobalParameterExample());
        Map<String, String> map = new HashMap<>(entities.size());
        if (entities.size() > 0) {
            for (GlobalParameter entity : entities) {
                map.put(entity.getCronKey(), entity.getCronValue());
            }
        }
        return map;
    }

    @Override
    public List<GenerationAwardParameter> getGenerationAward() {
        return generationAwardParameterMapper.selectByExample(new GenerationAwardParameterExample());
    }

    @Override
    public void updateGenerationAward(GenerationAwardParameter generationAwardParameter) {
        A.check(generationAwardParameterMapper.selectByPrimaryKey(generationAwardParameter.getId()) == null, "数据不存在");
        generationAwardParameter.setUpdateTime(new Date());
        generationAwardParameterMapper.updateByPrimaryKeySelective(generationAwardParameter);
    }

    @Override
    public List<LevelAwardParameter> getLevelAward() {
        LevelAwardParameterExample parameterExample = new LevelAwardParameterExample();
        parameterExample.setOrderByClause("id");
        return levelAwardParameterMapper.selectByExample(parameterExample);
    }

    @Override
    public void updateLevelAward(LevelAwardParameter levelAwardParameter) {
        A.check(levelAwardParameterMapper.selectByPrimaryKey(levelAwardParameter.getId()) == null, "数据不存在");
        levelAwardParameterMapper.updateByPrimaryKeySelective(levelAwardParameter);
    }
}
