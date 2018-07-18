package com.hengxunda.generalservice.service;

/**
 * 级差奖励service接口
 */
public interface ILevelAwardService {
    /**
     * 计算级差奖励
     *
     * @param mscRecordId 购买msc股权记录id
     */
    void levelAward(String mscRecordId);
}
