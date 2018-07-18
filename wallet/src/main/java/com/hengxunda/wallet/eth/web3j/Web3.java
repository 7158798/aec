package com.hengxunda.wallet.eth.web3j;


import com.hengxunda.wallet.eth.contract.AECoin;
import com.hengxunda.wallet.eth.contract.MSCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.response.NoOpProcessor;

/**
 * eth(ETH客户端)配置
 * Created by jerry on 2018/1/23.
 */
@SuppressWarnings("ALL")
@ConditionalOnProperty(value = "client-address", prefix = "web3j")
@Configuration
public class Web3 {

    @Autowired
    private Web3jConfig web3jConfig;

    @Bean
    public HttpService httpService() {
        return new HttpService(web3jConfig.getClientAddress());
    }

    @Bean
    public Web3j web3j(HttpService httpService) {
        return Web3j.build(httpService);
    }

    @Bean
    public Admin admin(HttpService httpService) {
        return Admin.build(httpService);
    }

    @ConditionalOnProperty(name = "address", prefix = "web3j.contracts.ms-coin")
    @Bean
    public MSCoin msCoin(Web3j web3j) {
        ContractConfig config = web3jConfig.getContracts().get("ms-coin");
        return MSCoin.load(config.getAddress(), web3j, new ClientTransactionManager(web3j, web3jConfig.getCoinBase(), new NoOpProcessor(web3j)),
                null, null);
//        return MSCoin.load(config.getAddress(), web3j, new ClientTransactionManager(web3j, web3jConfig.getCoinBase(), new NoOpProcessor(web3j)),
//                Contract.GAS_PRICE, Contract.GAS_LIMIT);
    }

    @ConditionalOnProperty(name = "address", prefix = "web3j.contracts.ae-coin")
    @Bean
    public AECoin aeCoin(Web3j web3j) {
        ContractConfig config = web3jConfig.getContracts().get("ae-coin");
//        return AECoin.load(config.getAddress(), web3j, new ClientTransactionManager(web3j, web3jConfig.getCoinBase(), new NoOpProcessor(web3j)),
//                Contract.GAS_PRICE, Contract.GAS_LIMIT);
        return AECoin.load(config.getAddress(), web3j, new ClientTransactionManager(web3j, web3jConfig.getCoinBase(), new NoOpProcessor(web3j)),
                null, null);
    }
}
