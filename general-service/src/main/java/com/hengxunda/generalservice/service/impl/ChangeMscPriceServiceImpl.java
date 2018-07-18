package com.hengxunda.generalservice.service.impl;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.Enum.MscEffectStatusEnum;
import com.hengxunda.common.Enum.MscRecordTypeEnum;
import com.hengxunda.common.utils.MathUtils;
import com.hengxunda.dao.entity.GlobalParameter;
import com.hengxunda.dao.entity.GlobalParameterExample;
import com.hengxunda.dao.entity.MscRecord;
import com.hengxunda.dao.entity.MscRecordExample;
import com.hengxunda.dao.mapper.GlobalParameterMapper;
import com.hengxunda.dao.mapper.MscRecordMapper;
import com.hengxunda.dao.mapper_custom.MscRecordCustomMapper;
import com.hengxunda.generalservice.service.IChangeMscPriceService;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * msc 改价service
 *
 * @author chengwei
 * @date 2018-06-15
 */
@Component
@Slf4j
public class ChangeMscPriceServiceImpl implements IChangeMscPriceService {

    @Autowired
    private IGlobalParameterService globalParameterService;

    @Autowired
    private MscRecordCustomMapper mscRecordCustomMapper;

    @Autowired
    private GlobalParameterMapper globalParameterMapper;

    @Autowired
    private MscRecordMapper mscRecordMapper;

    @Override
    @Transactional
    public void changePrice() {
        log.info("MSC改价逻辑开始...");

        //  MSC增长间隔
        GlobalParameter macIncreaseSplitParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MscIncreaseSplit.getCode());
        if (null == macIncreaseSplitParam) {
            log.info("获取MSC增长间隔参数失败");
            return;
        }

        //  MSC增长价格
        GlobalParameter macIncreasePriceParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MscIncreasePrice.getCode());
        if (null == macIncreasePriceParam) {
            log.info("获取MSC增长价格参数失败");
            return;
        }

        //  CNY买AEC价格
        GlobalParameter cnyBuyAecParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.AEC2CNY.getCode());
        if (null == cnyBuyAecParam) {
            log.info("获取CNY买AEC价格参数失败");
            return;
        }

        //  CNY买MSC价格
        GlobalParameter cnyBuyMscParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.MSC2CNY.getCode());
        if (null == cnyBuyMscParam) {
            log.info("获取CNY买MSC价格参数失败");
            return;
        }

        //  上次涨价剩余MSC个数
        GlobalParameter restMscAmountParam = globalParameterService.getGlobalParameterByKey(GlobalParameterEnum.RestMscAmount.getCode());
        if (null == cnyBuyMscParam) {
            log.info("MSC价格增长剩余基数参数失败");
            return;
        }

        BigDecimal increaseSplit = new BigDecimal(macIncreaseSplitParam.getCronValue());
        BigDecimal increasePrice = new BigDecimal(macIncreasePriceParam.getCronValue());
        BigDecimal cnyBuyAec = new BigDecimal(cnyBuyAecParam.getCronValue());
        BigDecimal cnyBuyMsc = new BigDecimal(cnyBuyMscParam.getCronValue());
        BigDecimal lastMscAmount = new BigDecimal(restMscAmountParam.getCronValue());

        //  用户AEC购买MSC总数
        BigDecimal curMscAmount = mscRecordCustomMapper.sumBuyMscAmount();
        if (null == curMscAmount || MathUtils.equalForBg(BigDecimal.ZERO, curMscAmount)) {
            log.info("Msc总量没有变化，改价结束");
            return;
        }

        //  当前MSC总数
        BigDecimal mscAmount = MathUtils.add8p(curMscAmount, lastMscAmount);

        log.info("当前系统MSC总数=" + mscAmount);

        //  增长步长，带小数
        BigDecimal divideDecimal = MathUtils.divide8p(mscAmount, increaseSplit);

        log.info("增长步长= " + divideDecimal);

        //  增长步长，整数部分
        int step = divideDecimal.intValue();

        if (step > 0) {
            //  增加价格
            BigDecimal incPrice = MathUtils.mul8p(increasePrice, new BigDecimal(step));

            //  重新计算cny买msc的价格
            BigDecimal newCnyBuyMsc = MathUtils.add8p(cnyBuyMsc, incPrice);
            log.info("修改后cny买msc价格=" + newCnyBuyMsc);

            //  重新计算aec买msc价格
            BigDecimal aecBuyMsc = MathUtils.divide8p(newCnyBuyMsc, cnyBuyAec);
            log.info("修改后aec买msc价格=" + aecBuyMsc);

            // 不计入涨价区间的msc数量
            BigDecimal resMscAmount = MathUtils.sub8p(mscAmount, MathUtils.mul8p(new BigDecimal(step), increaseSplit));
            if(MathUtils.equalForBg(resMscAmount,BigDecimal.ZERO)){
                resMscAmount = BigDecimal.ZERO;
            }
            log.info("不计入涨价区间的msc数量=" + resMscAmount);

            //  更新cny买msc的价格
            GlobalParameter updCnyBuyMscParam = new GlobalParameter();
            updCnyBuyMscParam.setCronValue(newCnyBuyMsc.toString());
            updCnyBuyMscParam.setUpdateTime(new Date());
            updCnyBuyMscParam.setUpdateUser("appUser");
            GlobalParameterExample updCnyBuyMscParameterExample = new GlobalParameterExample();
            updCnyBuyMscParameterExample.createCriteria().andCronKeyEqualTo(GlobalParameterEnum.MSC2CNY.getCode());
            globalParameterMapper.updateByExampleSelective(updCnyBuyMscParam, updCnyBuyMscParameterExample);

            //  保存aec买msc价格
            GlobalParameter aecBuyMscParam = new GlobalParameter();
            aecBuyMscParam.setCronValue(aecBuyMsc.toString());
            aecBuyMscParam.setUpdateTime(new Date());
            aecBuyMscParam.setUpdateUser("appUser");
            GlobalParameterExample globalParameterExample = new GlobalParameterExample();
            globalParameterExample.createCriteria().andCronKeyEqualTo(GlobalParameterEnum.MSC2AEC.getCode());
            globalParameterMapper.updateByExampleSelective(aecBuyMscParam, globalParameterExample);

            //  保存剩余不计入涨价区间的msc数量
            GlobalParameter updRestMscAmountParam = new GlobalParameter();
            updRestMscAmountParam.setCronValue(String.valueOf(resMscAmount.doubleValue()));
            updRestMscAmountParam.setUpdateTime(new Date());
            updRestMscAmountParam.setUpdateUser("appUser");
            GlobalParameterExample restMscAmountExample = new GlobalParameterExample();
            restMscAmountExample.createCriteria().andCronKeyEqualTo(GlobalParameterEnum.RestMscAmount.getCode());
            globalParameterMapper.updateByExampleSelective(updRestMscAmountParam, restMscAmountExample);

            //  设置AEC购买MSC统计标识为无效
            MscRecord mscRecord = new MscRecord();
            mscRecord.setEffectStatus(MscEffectStatusEnum.invalid.getCode());
            mscRecord.setUpdateTime(new Date());
            mscRecord.setUpdateUser("appUser");
            MscRecordExample mscRecordExample = new MscRecordExample();
            mscRecordExample.createCriteria()
                    .andEffectStatusEqualTo(MscEffectStatusEnum.valid.getCode())
                    .andTypeEqualTo(MscRecordTypeEnum.bugMsc.getCode());
            mscRecordMapper.updateByExampleSelective(mscRecord, mscRecordExample);
        } else {
            log.info("MSC数量未跨区间，无需改价");
        }
        log.info("MSC改价逻辑结束...");
    }

}
