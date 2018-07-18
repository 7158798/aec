package com.hengxunda.web.config;

import com.hengxunda.common.Enum.WalletTypeEnum;
import com.hengxunda.common.utils.PasswordUtil;
import com.hengxunda.common.utils.UUIDUtils;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.WalletInfo;
import com.hengxunda.dao.entity.WalletPresetAddress;
import com.hengxunda.dao.mapper.WalletInfoMapper;
import com.hengxunda.dao.mapper.WalletPresetAddressMapper;
import com.hengxunda.dao.mapper_custom.WalletPresetAddressCustomMapper;
import com.hengxunda.wallet.btc.BTCHelper;
import com.hengxunda.wallet.btc.LTCHelper;
import com.hengxunda.wallet.eth.ETHHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class WalletAsync {

    @Autowired
    private BTCHelper btcHelper;

    @Autowired
    private LTCHelper ltcHelper;

    @Autowired
    private ETHHelper ethHelper;

    @Autowired
    private WalletPresetAddressMapper walletPresetAddressMapper;
    @Autowired
    private WalletPresetAddressCustomMapper walletPresetAddressCustomMapper;

    @Value("${preset-password}")
    private String presetPassword;

    /**
     * btc钱包预置地址生成
     */
   /* @Async*/
    public void BTCWalletAddress(Long mun) {
        String btcAddress = btcHelper.getAccountAddress("wqt_btc_user" + mun);
        if (btcAddress == null) {
            return;
        }

        WalletPresetAddress walletPresetAddress = new WalletPresetAddress();
        walletPresetAddress.setAddress(btcAddress);
        walletPresetAddress.setStatus(1);
        walletPresetAddress.setType(WalletTypeEnum.BTC.getCode());
        walletPresetAddress.setAccount("wqt_btc_user" + mun);
        walletPresetAddressMapper.insertSelective(walletPresetAddress);
    }

    /**
     * @Author:zhangwenjun:
     * @Description: 获取BTC地址对象
     * @Param :
     * @Date 2018/6/20 20:18
     */
    public WalletPresetAddress BTCWalletAddressBatch(Long mun) {
        WalletPresetAddress walletPresetAddress = null;
        String btcAddress = btcHelper.getAccountAddress("wqt_btc_user" + mun);
        if (btcAddress != null) {
            walletPresetAddress = new WalletPresetAddress();
            walletPresetAddress.setAddress(btcAddress);
            walletPresetAddress.setStatus(1);
            walletPresetAddress.setType(WalletTypeEnum.BTC.getCode());
            walletPresetAddress.setAccount("wqt_btc_user" + mun);
        }

        return walletPresetAddress;
    }

    /**
     * @Author:zhangwenjun:
     * @Description: 获取莱特币地址对象
     * @Param :
     * @Date 2018/6/21 12:09
     */
    public WalletPresetAddress LTCWalletAddressBatch(Long mun) {
        WalletPresetAddress walletPresetAddress = null;
        String ltcAddress = ltcHelper.getAccountAddress("wqt_ltc_user" + mun);
        if (ltcAddress != null) {
            walletPresetAddress = new WalletPresetAddress();
            walletPresetAddress.setAddress(ltcAddress);
            walletPresetAddress.setStatus(1);
            walletPresetAddress.setType(WalletTypeEnum.LTC.getCode());
            walletPresetAddress.setAccount("wqt_ltc_user" + mun);
        }

        return walletPresetAddress;
    }

    /**
     * @Author:zhangwenjun:
     * @Description: 异步生成地址
     * @Param :
     * @Date 2018/6/21 9:38
     */
    @Async
    public synchronized void BTCWalletAddressAsync(Long num) {
        if (num > 0) {
            log.info("sync method start...");
            //获取生成最大值
            Long oldNum = this.getWalletAddressMaxAccountId(WalletTypeEnum.BTC.getCode());
            List<WalletPresetAddress> walletPresetAddressList = new ArrayList<WalletPresetAddress>();
            for (long i = 1; i <= num; i++) {
                log.info("sync method doing..."+i);
                WalletPresetAddress walletPresetAddress = this.BTCWalletAddressBatch(i + oldNum);
                walletPresetAddressList.add(walletPresetAddress);
            }

            this.saveWalletAddressBatch(walletPresetAddressList);
            log.info("sync method end...");
        }
    }

    @Async
    public synchronized void LTCWalletAddressAsync(Long num) {
        if (num > 0) {
            log.info("sync method start...");
            //获取生成最大值
            Long oldNum = this.getWalletAddressMaxAccountId(WalletTypeEnum.LTC.getCode());
            List<WalletPresetAddress> walletPresetAddressList = new ArrayList<WalletPresetAddress>();
            for (long i = 1; i <= num; i++) {
                log.info("sync method doing..."+i);
                WalletPresetAddress walletPresetAddress = this.LTCWalletAddressBatch(i + oldNum);
                walletPresetAddressList.add(walletPresetAddress);
            }

            this.saveWalletAddressBatch(walletPresetAddressList);
            log.info("sync method end...");

        }
    }

    /**
     * ltc钱包单地址插入
     */
    @Async
    public void LTCWalletAddress(Long mun) {
        String ltcAddress = ltcHelper.getAccountAddress("wqt_ltc_user" + mun);
        if (ltcAddress == null) {
            return;
        }
        WalletPresetAddress walletPresetAddress = new WalletPresetAddress();
        walletPresetAddress.setAddress(ltcAddress);
        walletPresetAddress.setStatus(1);
        walletPresetAddress.setType(WalletTypeEnum.LTC.getCode());
        walletPresetAddress.setAccount("wqt_ltc_user" + mun);
        walletPresetAddressMapper.insertSelective(walletPresetAddress);
    }

    /**
     * 注册ETH钱包地址
     */
    @Async
    public void ETHWalletAddress(String walletPasssword) {
        if (walletPasssword == null) {
            walletPasssword = presetPassword;
        }

        String file = ethHelper.createWallet(walletPasssword);
        Credentials credentials = ethHelper.getCredentials(file, walletPasssword);

        WalletPresetAddress ETHwalletPresetAddress = new WalletPresetAddress();
        ETHwalletPresetAddress.setAddress(credentials.getAddress());
        ETHwalletPresetAddress.setStatus(1);
        ETHwalletPresetAddress.setType(WalletTypeEnum.ETH.getCode());
        //AECwalletInfo.setWalletPassword(walletPasssword).setWalletFile(file);
        walletPresetAddressMapper.insertSelective(ETHwalletPresetAddress);


    }

    /**
     * @Author:zhangwenjun:
     * @Description: 获取以太坊地址对象
     * @Param :
     * @Date 2018/6/21 14:19
     */
    public WalletPresetAddress getETHWalletAddress(String walletPasssword) {

        if (walletPasssword == null) {
            walletPasssword = presetPassword;
        }

        String file = ethHelper.createWallet(walletPasssword);
        Credentials credentials = ethHelper.getCredentials(file, walletPasssword);

        WalletPresetAddress ETHwalletPresetAddress = new WalletPresetAddress();
        ETHwalletPresetAddress.setAddress(credentials.getAddress());
        ETHwalletPresetAddress.setStatus(1);
        ETHwalletPresetAddress.setType(WalletTypeEnum.ETH.getCode());
        return ETHwalletPresetAddress;
    }

    @Async
    public synchronized void ETHWalletAddressAsync(Long num, String password) {
        if (num > 0) {
            List<WalletPresetAddress> walletPresetAddressList = new ArrayList<WalletPresetAddress>();
            for (long i = 1; i <= num; i++) {
                log.info("sync method create address doing..."+i);
                WalletPresetAddress walletPresetAddress = this.getETHWalletAddress(password);
                walletPresetAddressList.add(walletPresetAddress);
            }
            this.saveWalletAddressBatch(walletPresetAddressList);

        }
    }

    /**
     * @Author:zhangwenjun:
     * @Description: 批量插入
     * @Param :
     * @Date 2018/6/20 20:37
     */
    public void saveWalletAddressBatch(List<WalletPresetAddress> walletPresetAddress) {
        walletPresetAddressCustomMapper.batchInsertAddress(walletPresetAddress);
    }

    public Long getWalletAddressMaxAccountId(String type) {

        return walletPresetAddressCustomMapper.getWalletAddressMaxAccountId(type);
    }
}
