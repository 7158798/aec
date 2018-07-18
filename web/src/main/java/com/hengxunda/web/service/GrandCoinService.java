package com.hengxunda.web.service;

import com.hengxunda.web.vo.GrantCoinTypeVo;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * 拨币
 * @Author: QiuJY
 * @Date: Created in 11:04 2018/6/15
 */
public interface GrandCoinService {

    Map<String,Object> check(List<GrantCoinTypeVo> list);

    void grantCoin(List<GrantCoinTypeVo> list,String adminId);
}
