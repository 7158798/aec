package com.hengxunda.app.service;

import com.github.pagehelper.PageInfo;
import com.hengxunda.common.utils.CommonResponse;
import com.hengxunda.dao.po.app.BbAdvertPo;
import com.hengxunda.dao.po.app.BbAdvertVo;
import com.hengxunda.dao.po.app.BbTransPo;

import java.math.BigDecimal;

/**
 * 币币交易广告service
 *
 * @author chengwei
 * @date 2018-07-06
 */
public interface IBbAdvertService {

    PageInfo<BbAdvertVo> queryListByPage(String token, Integer type, Integer pageNo, Integer pageSize);

    PageInfo<BbTransPo> queryTransListByPage(Integer pageNo, Integer pageSize);

    CommonResponse<String> insertAdvert(BbAdvertPo bbAdvertPo);

    CommonResponse<String> trans(BbTransPo bbTransPo);

    CommonResponse<String> deleteAdvert(String advertId);

    PageInfo<BbAdvertVo> queryMineAdvertListByPage(Integer pageNo, Integer pageSize);

    BigDecimal queryLastTransPrice();
}
