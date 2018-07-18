package com.hengxunda.wallet.eth;


import com.hengxunda.common.exception.ServerException;
import com.hengxunda.wallet.eth.entity.BCWalletEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Component
public class ETHHelper {
    private static Logger logger = LoggerFactory.getLogger(ETHHelper.class);

    private static final BigInteger gasLimit = Convert.toWei(BigDecimal.valueOf(900000), Convert.Unit.WEI).toBigInteger();

    @Value("${eth.keystore}")
    private String keystore;

     @Autowired
    private Admin admin;

    /**
     * 创建钱包
     * @param password 密码
     * @return 钱包文件地址
     */
    public String createWallet(String password) {
        File destinationDirectory = new File(keystore);
        try {
            return WalletUtils.generateNewWalletFile(password, destinationDirectory, true);
        } catch (Exception e) {
            throw new ServerException("创建以太坊钱包错误，错误原因：" + e.getMessage(), e);
        }
    }

    public Credentials getCredentials(String fileName, String password) {
        try {
            return WalletUtils.loadCredentials(password, keystore + "/" + fileName);
        } catch (Exception e) {
            throw new ServerException("加载以太坊钱包错误，错误原因：" + e.getMessage(), e);
        }
    }


    public String transfer(BCWalletEntity wallet, String to, BigDecimal value) {
        try {
            Credentials credentials = this.getCredentials(wallet.getFile(), wallet.getPassword());

            Transfer transfer = new Transfer(admin, new FastRawTransactionManager(admin, credentials, new NoOpProcessor(admin)));
            TransactionReceipt receipt = transfer.sendFunds(to, value, Convert.Unit.ETHER).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    public BigDecimal getBalance(BCWalletEntity wallet) {
        try {
            Credentials credentials = this.getCredentials(wallet.getFile(), wallet.getPassword());
            EthGetBalance getBalance = admin.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            return Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
        } catch (Exception e) {
            throw new ServerException("获取以太坊余额错误，错误原因：" + e.getMessage(), e);
        }

    }

    public Transaction getTransaction(String transactionHash) {
        try {
            return admin.ethGetTransactionByHash(transactionHash).send().getResult();
        } catch (Exception e) {
            throw new ServerException("获取以太坊交易错误，错误原因：" + e.getMessage(), e);
        }
    }

    public TransactionReceipt getTransactionReceipt(String transactionHash) {
        try {
            Optional<TransactionReceipt> optional = admin.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt();
            return optional.orElse(null);
        } catch (Exception e) {
            throw new ServerException("获取以太坊交易错误，错误原因：" + e.getMessage(), e);
        }
    }

    public EthBlock.Block getLatestBlock() {
        try {
            return admin.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock();
        } catch (Exception e) {
            throw new ServerException("获取以太坊区块错误，错误原因：" + e.getMessage(), e);
        }
    }

    public EthBlock.Block getBlock(BigInteger blockNumber) {
        try {
            return admin.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send().getBlock();
        } catch (Exception e) {
            throw new ServerException("获取以太坊区块错误，错误原因：" + e.getMessage(), e);
        }
    }

  /*  public static void main(String[] args) throws Exception {
        Web3j web3 = Web3j.build(new HttpService("http://47.92.73.26:3331"));
        //Web3j web3 = Web3j.build(new HttpService("http://120.79.145.234:3333"));
        //Web3j web3 = Web3j.build(new HttpService("http://192.168.1.254:2222"));
        //ETHHelper ethHelper = new ETHHelper(web3);
        //ethHelper.keystore = "d:/桌面/456";

//        String password = "234567";
//        String fileName = ethHelper.createWallet(password);
//        Credentials credentials = ethHelper.getCredentials(fileName, password);
//        System.out.println(credentials.getEcKeyPair().getPrivateKey());
//        System.out.println(credentials.getEcKeyPair().getPublicKey());
//
//        System.out.println("fileName:" + fileName + ", address:" + credentials.getAddress());

        EthBlock.Block block = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock();
        System.out.println(block.getNumber());
//        System.out.println(block.getNonce());
//        System.out.println(((Transaction)block.getTransactions().get(1).get()).getTo());
    }*/
}
