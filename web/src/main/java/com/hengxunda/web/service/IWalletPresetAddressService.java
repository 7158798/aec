package com.hengxunda.web.service;

import com.hengxunda.web.vo.AddressStatisticsVo;

import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 */
public interface IWalletPresetAddressService {
    List<AddressStatisticsVo> getStatisticsAddress();
}
