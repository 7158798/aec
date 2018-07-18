package com.hengxunda.app.config;

import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.wallet.btc.BTCHelper;
import com.hengxunda.wallet.btc.LTCHelper;
import com.hengxunda.wallet.eth.ETHHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

import java.util.Date;



@Component
public class WalletAsync {

    @Autowired
    private BTCHelper btcHelper;

    @Autowired
    private LTCHelper ltcHelper;

    @Autowired
    private ETHHelper ethHelper;

    @Autowired
    private WalletInfoMapper walletInfoMapper;

    /**
     * 注册btc钱包地址
     * @param currentTime
     * @param user
     */
    @Async
    public void BTCWalletAddress(Date currentTime, User user) {
        String btcAddress = btcHelper.getAccountAddress(user.getId());
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setId(UUIDUtils.getUUID()).setUserId(user.getId()).setAddress(btcAddress).setType(WalletTypeEnum.BTC.getCode()).setCreateTime(currentTime);
        walletInfoMapper.insertSelective(walletInfo);
    }

    /**
     * 注册ltc钱包地址
     * @param currentTime
     * @param user
     */
    @Async
    public void LTCWalletAddress(Date currentTime, User user) {
        String btcAddress = ltcHelper.getAccountAddress(user.getId());
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setId(UUIDUtils.getUUID()).setUserId(user.getId()).setAddress(btcAddress).setType(WalletTypeEnum.LTC.getCode()).setCreateTime(currentTime);
        walletInfoMapper.insertSelective(walletInfo);
    }

    /**
     * 注册aec,msc钱包地址
     * @param currentTime
     * @param user
     */
    @Async
    public void AECAndMSCWalletAddress(Date currentTime, User user) {
        String walletPasssword = PasswordUtil.getSalt();
        String file = ethHelper.createWallet(walletPasssword);
        Credentials credentials = ethHelper.getCredentials(file,walletPasssword);

        WalletInfo AECwalletInfo = new WalletInfo();
        AECwalletInfo.setId(UUIDUtils.getUUID()).setUserId(user.getId()).setAddress(credentials.getAddress()).setType(WalletTypeEnum.AEC.getCode()).setCreateTime(currentTime);
        AECwalletInfo.setWalletPassword(walletPasssword).setWalletFile(file);
        walletInfoMapper.insertSelective(AECwalletInfo);

        WalletInfo MSCwalletInfo = new WalletInfo();
        MSCwalletInfo.setId(UUIDUtils.getUUID()).setUserId(user.getId()).setAddress(credentials.getAddress()).setType(WalletTypeEnum.MSC.getCode()).setCreateTime(currentTime);
        MSCwalletInfo.setWalletPassword(walletPasssword).setWalletFile(file);
        walletInfoMapper.insertSelective(MSCwalletInfo);

    }




}
