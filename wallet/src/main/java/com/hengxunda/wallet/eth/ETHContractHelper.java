package com.hengxunda.wallet.eth;

import com.hengxunda.common.exception.BusinessException;
import com.hengxunda.common.exception.ServerException;
import com.hengxunda.common.utils.A;
import com.hengxunda.wallet.eth.contract.AECoin;
import com.hengxunda.wallet.eth.contract.MSCoin;
import com.hengxunda.wallet.eth.web3j.Web3jConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class ETHContractHelper {
    private static Logger logger = LoggerFactory.getLogger(ETHHelper.class);

    private static final BigInteger gasLimit = Convert.toWei(BigDecimal.valueOf(900000), Convert.Unit.WEI).toBigInteger();

    @Autowired
    private Web3jConfig web3jConfig;

    @Autowired
    private Admin admin;

    @Autowired
    private AECoin aeCoin;

    @Autowired
    private MSCoin msCoin;

    public String transferAecFormBaseCoin(String _to, double amount) {
        A.checkParam(StringUtils.isEmpty(_to), "目标账户地址不能为空");
        A.checkParam(amount <= 0L, "交易金额不正确");

        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(web3jConfig.getCoinBase(),
                    web3jConfig.getCoinBasePassword()).send();

            A.check(!personalUnlockAccount.accountUnlocked(), "密码错误");

            BigInteger wei = Convert.toWei(BigDecimal.valueOf(amount), Convert.Unit.ETHER).toBigInteger();

            TransactionReceipt receipt = this.aeCoin.transfer(_to, wei).send();

            return receipt.getTransactionHash();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("调用以太坊[aeCoin.transfer]错误，错误原因：" + e.getMessage(), e);
        }
    }

    public String transferMscFormBaseCoin(String _to, double amount) {
        A.checkParam(StringUtils.isEmpty(_to), "目标账户地址不能为空");
        A.checkParam(amount <= 0L, "交易金额不正确");

        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(web3jConfig.getCoinBase(),
                    web3jConfig.getCoinBasePassword()).send();

            A.check(!personalUnlockAccount.accountUnlocked(), "密码错误");

            BigInteger wei = Convert.toWei(BigDecimal.valueOf(amount), Convert.Unit.ETHER).toBigInteger();

            TransactionReceipt receipt = this.msCoin.transfer(_to, wei).send();

            return receipt.getTransactionHash();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("调用以太坊[msCoin.transfer]错误，错误原因：" + e.getMessage(), e);
        }
    }
}
