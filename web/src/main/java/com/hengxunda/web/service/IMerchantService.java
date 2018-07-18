package com.hengxunda.web.service;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.po.web.AdvertWebPo;
import com.hengxunda.dao.po.web.BondApplyPo;
import com.hengxunda.dao.po.web.MerchantApplyPo;
import com.hengxunda.web.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Date: create in 2018/6/7
 */
public interface IMerchantService {

    //保证金
    BondApplyListVo getBondApplys(String mobile, String name, int type, int status,
                                  BigDecimal change, BigDecimal original, Page page);

    List<BondApplyPo> downloadBondApplys(String mobile, String name, int type, int status,
                                         BigDecimal change, BigDecimal original, Date beginTime, Date endTime);

    void reviewBondApply(String id, String userId, Integer status, Integer type, BigDecimal amount, String reason);

    //广告
    AdvertListVo getAdvertWebs(String mobile, String name, BigDecimal bond, int type, int status,
                               BigDecimal minValue, BigDecimal price, Date createTime, Page page);

    List<AdvertWebPo> downloadAdverts(String mobile, String name, BigDecimal bond, int type, int status,
                                      BigDecimal minValue, BigDecimal price, Date beginTime, Date endTime);

    //申请
    MerchantApplyVo getMerchantApplys(String uid, String mobile, String name, int status, Page page);

    MerchantDetailVo getMerchantApplyInfoById(String id);

    int reviewMerchantApply(String id, int status) throws Exception;

    List<MerchantApplyPo> downlodApllys(String uid, String mobile, String name, int status, Date beginTime, Date endTime);

    //银商信息
    MerchantListVo getMerchants(String id, String mobile, String name, String nickName, String creatTime, Page page);

    MerchantDetailVo getMerchantDetailById(String id);

    List<MerchantVo> downLoadExcel(String userId, String mobile, String name, String nickName, Date beginTime, Date endTime, HttpServletResponse response);
}
