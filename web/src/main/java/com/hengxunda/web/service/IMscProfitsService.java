package com.hengxunda.web.service;

import com.github.pagehelper.PageInfo;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.po.web.MscProfitsPo;

/**
 * 股权分红service接口
 *
 * @author chengwei
 * @date 2018-06-07
 */
public interface IMscProfitsService {
    PageInfo<MscProfitsPo> queryListByPage(Integer pageNo, Integer pageSize);

    void addMscProfits(MscProfitsPo mscProfitsPo);

    CommonResponse<String> updateMscProfits(MscProfitsPo mscProfitsPo);

    CommonResponse<String> deleteMscProfits(MscProfitsPo mscProfitsPo);

    CommonResponse<String> allotMscProfits(MscProfitsPo mscProfitsPo);
}
