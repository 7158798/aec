package com.hengxunda.wallet.eth.contract;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;


public class MSCoin extends ETHCoin {
    protected MSCoin(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MSCoin(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MSCoin load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MSCoin(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static MSCoin load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MSCoin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
