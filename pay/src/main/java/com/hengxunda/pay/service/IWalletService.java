package com.hengxunda.pay.service;

import com.hengxunda.pay.dto.BalanceDto;
import com.hengxunda.pay.dto.BringCoinDto;
import com.hengxunda.pay.dto.UserPayDto;
import com.hengxunda.pay.vo.BalanceVo;

public interface IWalletService {

    void pay(UserPayDto userPayDto);

    /**
     * 查询用户余额和冻结金额
     * @param balanceDto
     * @return
     */
    BalanceVo balance(BalanceDto balanceDto);

    void bringCoin(BringCoinDto bringCoinDto);
}
