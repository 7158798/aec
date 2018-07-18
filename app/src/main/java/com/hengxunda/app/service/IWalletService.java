package com.hengxunda.app.service;

import com.hengxunda.app.dto.AKeyTurnAecDto;
import com.hengxunda.app.dto.AKeyTurnMscDto;
import com.hengxunda.app.dto.TradeDto;
import com.hengxunda.app.vo.CoinRateVo;
import com.hengxunda.app.vo.OrderInfoVo;
import com.hengxunda.app.vo.WalletInfoVo;
import com.hengxunda.dao.po.app.WalletRecordPo;

import java.util.List;

/**
 * 钱包信息接口
 */
public interface IWalletService {
    /**
     * 获取钱包记录信息
     */
    List<WalletRecordPo> getWalletRecordByUserId(Integer pageNo, Integer pageSize);

    /**
     * 获取某个用户的钱包信息
     * @return
     */
    WalletInfoVo getWalletInfoByUserId();

    /**
     * 提币
     * @return
     */
    void bringCoin(TradeDto tradeDto);

    /**
     * 转账、付款操作
     * @param tradeDto
     */
    void transfer(TradeDto tradeDto);

    /**
     *一键转币（其他币转AEC币）
     */
    public void aKeyTurnAec(AKeyTurnAecDto aKeyTurnAecDto);

    /**
     * 一键转币（AEC币转MSC币）
     */
    public void aecTrunMsc(AKeyTurnMscDto aKeyTurnMscDto);

    /**
     * 获取币币交易汇率
     * @return
     */
    public List<CoinRateVo> coinRate();

    /**
     * 返回用户订单信息
     * @param orderId
     * @return
     */
    public OrderInfoVo orderInfo(String orderId);
}
