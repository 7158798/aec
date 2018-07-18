package com.hengxunda.generalservice.service.impl;

import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.mapper.MscRecordMapper;
import com.hengxunda.generalservice.service.IAwardService;
import com.hengxunda.generalservice.service.IChangeMscPriceService;
import com.hengxunda.generalservice.service.IGenerationAwardService;
import com.hengxunda.generalservice.service.ILevelAwardService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 一键换币触发 改价、级差、代数奖励
 *
 * @author chengwei
 * @date 2018-06-26
 */
@Component
@Slf4j
public class AwardServiceImpl implements IAwardService {

    @Autowired
    private ILevelAwardService levelAwardService;

    @Autowired
    private IChangeMscPriceService changeMscPriceService;

    @Autowired
    private IGenerationAwardService generationAwardService;

    @Autowired
    private MscRecordMapper mscRecordMapper;

    @Autowired
    private RedissonClient redissonClient;

    private static final String awardLock = "common_award_lock";

    @Override
    @Async
    public void syncExec(MscRecord mscRecord) {

        RLock lock = redissonClient.getLock(awardLock);
        try {
            log.info("一键换币触发 改价、级差、代数奖励 获取锁...");
            lock.lock();
            log.info("一键换币触发 改价、级差、代数奖励开始...");
            mscRecordMapper.insertSelective(mscRecord);
            changeMscPriceService.changePrice();
            levelAwardService.levelAward(mscRecord.getId());
            generationAwardService.generationAward(mscRecord.getId());
            log.info("一键换币触发 改价、级差、代数奖励结束...");
        } catch (Exception e) {
            log.error("键换币触发 改价、级差、代数奖励异常：" + e.getMessage());
        } finally {
            lock.unlock();
            log.info("一键换币触发 改价、级差、代数奖励 释放锁...");
        }

    }
}
