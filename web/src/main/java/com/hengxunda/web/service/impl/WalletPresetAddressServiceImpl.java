package com.hengxunda.web.service.impl;

import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.dao.mapper_custom.WalletPresetAddressCustomMapper;
import com.hengxunda.web.service.IWalletPresetAddressService;
import com.hengxunda.web.vo.AddressStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 */
@Slf4j
@Service
public class WalletPresetAddressServiceImpl implements IWalletPresetAddressService {

    @Autowired
    private WalletPresetAddressCustomMapper walletPresetAddressCustomMapper;

    @Override
    public List<AddressStatisticsVo> getStatisticsAddress() {
        List<AddressStatisticsVo> addressStatisticsVolist=new ArrayList<AddressStatisticsVo>();
        //ETH
        AddressStatisticsVo addressStatisticsVo=new AddressStatisticsVo();
        addressStatisticsVo.setType(WalletTypeEnum.ETH.getCode());
        addressStatisticsVo.setTolalAddress(walletPresetAddressCustomMapper.countAddresTotal(WalletTypeEnum.ETH.getCode()));
        addressStatisticsVo.setSurplusAddress(walletPresetAddressCustomMapper.countAddresTotalNoUse(WalletTypeEnum.ETH.getCode()));
        //BTC
        AddressStatisticsVo addressStatisticsVo1=new AddressStatisticsVo();
        addressStatisticsVo1.setType(WalletTypeEnum.BTC.getCode());
        addressStatisticsVo1.setTolalAddress(walletPresetAddressCustomMapper.countAddresTotal(WalletTypeEnum.BTC.getCode()));
        addressStatisticsVo1.setSurplusAddress(walletPresetAddressCustomMapper.countAddresTotalNoUse(WalletTypeEnum.BTC.getCode()));
        //LTC
        AddressStatisticsVo addressStatisticsVo2=new AddressStatisticsVo();
        addressStatisticsVo2.setType(WalletTypeEnum.LTC.getCode());
        addressStatisticsVo2.setTolalAddress(walletPresetAddressCustomMapper.countAddresTotal(WalletTypeEnum.LTC.getCode()));
        addressStatisticsVo2.setSurplusAddress(walletPresetAddressCustomMapper.countAddresTotalNoUse(WalletTypeEnum.LTC.getCode()));
        addressStatisticsVolist.add(addressStatisticsVo);
        addressStatisticsVolist.add(addressStatisticsVo1);
        addressStatisticsVolist.add(addressStatisticsVo2);
        return addressStatisticsVolist;
    }
}
