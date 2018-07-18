package com.hengxunda.generalservice.service;

/**
 * 代数奖励service接口
 *
 * @author chengwei
 * @date 2018-06-11
 */
public interface IGenerationAwardService {
    /**
     * 计算代数奖励
     *
     * @param mscRecordId 购买msc股权记录id
     */
    void generationAward(String mscRecordId);
}
