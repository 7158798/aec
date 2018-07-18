package com.hengxunda.wapp.service;

import com.hengxunda.dao.po.app.WalletRecordPo;
import com.hengxunda.wapp.dto.AKeyTurnAecDto;
import com.hengxunda.wapp.dto.AKeyTurnMscDto;
import com.hengxunda.wapp.dto.TradeDto;
import com.hengxunda.wapp.vo.CoinRateVo;
import com.hengxunda.wapp.vo.WalletInfoVo;

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

}
