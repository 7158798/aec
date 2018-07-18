package com.hengxunda.dao.mapper_custom;

import com.hengxunda.common.utils.Page;
import com.hengxunda.dao.entity.Advert;
import com.hengxunda.dao.po.AdvertListPo;
import com.hengxunda.dao.po.app.*;
import com.hengxunda.dao.po.web.AdvertWebPo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AdvertCustomMapper {

    List<AdvertListPo> findList(@Param("advert") Advert advert, @Param("transactionPair") String transactionPair, @Param("online") Integer online, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    //app获取广告列表
    List<AdvertPo> getAdverList(@Param("type") Integer type, @Param("transactionPair") String transactionPair, @Param("startRow") Integer startRow, @Param("rows") Integer rows);

    //app获取广告数量
    int countAdvertList(@Param("type") Integer type, @Param("transactionPair") String transactionPair);

    int updateAdvertEnableStockById(@Param("amount") BigDecimal amount, @Param("advertId") String advertId);

    AdvertOfOrderPo getAdvertOfOrderInfo(@Param("advertId") String advertId);

    PayInfoPo getPayInfo(@Param("transactionPair") String transactionPair, @Param("userId") String userId);


    //web获取广告列表信息
    List<AdvertWebPo> getAdvertsForWeb(@Param("record") AdvertWebPo record, @Param("page") Page page);

    //web统计广告数量
    int countAdvertsForWeb(@Param("record") AdvertWebPo record);

    //web获取广告下载信息
    List<AdvertWebPo> getAdvertsForDownload(@Param("record") AdvertWebPo record);

    List<BankInfoPo> getBankInfo(@Param("userId") String userId);

    List<AlipayInfoPo> getAlipayInfo(String userId);

    //app获取广告列表
    List<AdvertPo> getAdverList1(@Param("type") Integer type, @Param("transactionPair") String transactionPair, @Param("startRow") Integer startRow, @Param("rows") Integer rows);

    int countAdvertList1(@Param("type") Integer type, @Param("transactionPair") String transactionPair);

    int existsUnitPrice(@Param("type") Integer type, @Param("unitPrice") BigDecimal unitPrice, @Param("createUser") String createUser, @Param("id") String id);

    Integer getCompetedOrder(@Param("advertUserId") String advertUserId, @Param("time") Date time);

}