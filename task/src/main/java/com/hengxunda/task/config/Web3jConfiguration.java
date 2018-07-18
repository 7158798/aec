package com.hengxunda.task.config;


import com.hengxunda.task.service.AECoinTransferService;
import com.hengxunda.task.service.EthTransactionReceiptService;
import com.hengxunda.task.service.MSCoinTransferService;
import com.hengxunda.task.subscription.AECoinTransferLazySubscribe;
import com.hengxunda.task.subscription.MSCoinTransferLazySubscribe;
import com.hengxunda.wallet.eth.contract.AECoin;
import com.hengxunda.wallet.eth.contract.MSCoin;
import com.hengxunda.wallet.eth.web3j.ContractConfig;
import com.hengxunda.wallet.eth.web3j.Web3jConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;



@SuppressWarnings("ALL")
@ConditionalOnProperty(value = "client-address", prefix = "web3j")
@Configuration
public class Web3jConfiguration {

    @Autowired
    private Web3jConfig web3jConfig;

    @Bean
    public HttpService httpService() {
        return new HttpService(web3jConfig.getClientAddress());
    }



    @Bean
    public EthTransactionReceiptProcessor ethTransactionReceiptProcessor(Web3j web3j,
                                                                         EthTransactionReceiptService ethTransactionReceiptService) {
        return new EthTransactionReceiptProcessor(web3j,
                ethTransactionReceiptService,
                web3jConfig.getPollingAttemptsPerTxHash(),
                web3jConfig.getPollingFrequency(),
                web3jConfig.isPollingEnable());
    }

    @Bean
    public ClientTransactionManager clientTransactionManager(Web3j web3j,
                                                             EthTransactionReceiptProcessor ethTransactionReceiptProcessor) throws Exception {
        return new ClientTransactionManager(web3j, web3jConfig.getCoinBase(), ethTransactionReceiptProcessor);
    }

    @ConditionalOnProperty(name = "address", prefix = "web3j.contracts.ms-coin")
    @Bean
    public MSCoin msCoin(Web3j web3j, ClientTransactionManager transactionManager) {
        ContractConfig config = web3jConfig.getContracts().get("ms-coin");
        return MSCoin.load(config.getAddress(), web3j, transactionManager,
                null, null);
    }

    @ConditionalOnProperty(name = "address", prefix = "web3j.contracts.ae-coin")
    @Bean
    public AECoin aeCoin(Web3j web3j, ClientTransactionManager transactionManager) {
        ContractConfig config = web3jConfig.getContracts().get("ae-coin");
        return AECoin.load(config.getAddress(), web3j, transactionManager,
                null, null);
    }

    @ConditionalOnBean({MSCoin.class, MSCoinTransferService.class})
    @Bean
    public MSCoinTransferLazySubscribe msCoinTransferLazySubscribe(Web3j web3j, MSCoin msCoin, MSCoinTransferService msCoinTransferService) {
        return new MSCoinTransferLazySubscribe(web3j, msCoin, msCoinTransferService);
    }

    @ConditionalOnBean({AECoin.class, AECoinTransferService.class})
    @Bean
    public AECoinTransferLazySubscribe aeCoinTransferLazySubscribe(Web3j web3j, AECoin aeCoin, AECoinTransferService aeCoinTransferService) {
        return new AECoinTransferLazySubscribe(web3j, aeCoin, aeCoinTransferService);
    }
}