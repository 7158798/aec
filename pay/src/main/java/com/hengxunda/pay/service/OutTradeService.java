package com.hengxunda.pay.service;

import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.pay.dto.OutTradeDto;
import com.hengxunda.pay.dto.TradeDto;
import com.hengxunda.pay.vo.OutTradeVo;

/**
 * 外部交易
 *
 * @Author: QiuJY
 * @Date: Created in 14:40 2018/3/20
 */
public interface OutTradeService {

    String checkById(String id);

    OutTradeVo getTradeNo(OutTradeDto outTradeDto,User user);

    OutTradeVo getTrade(OutTradeDto outTradeDto);

    OutTradeVo grantCoin(OutTradeDto outTradeDto,User user);

    /**
     * MSC、AEC交易
     * @param tradeDto
     */
    void transfer(TradeDto tradeDto, WalletInfo fromWalletInfo, WalletInfo toWalletInfo);
}
