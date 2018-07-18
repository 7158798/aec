package com.hengxunda.wallet.eth.web3j;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@ConfigurationProperties(prefix = "web3j")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Web3jConfig {

    /** 默认账户(主账户)地址 */
    private String coinBase;

    /** 默认账户(主账户)密码 */
    private String coinBasePassword;

    /** 以太坊客户端节点地址：http://localhost:8545 */
    private String clientAddress;

    /** 是否启用admin api(暂时不起作用) */
    private boolean adminClient;

    /** JSON RPC HTTP 请求超时时间(秒) */
    private long httpTimeoutSeconds;

    /** 交易(收据)轮训尝试次数 */
    private int pollingAttemptsPerTxHash;

    /** 轮训频率(毫秒) */
    private long pollingFrequency;

    /** 是否开启轮训 */
    private boolean pollingEnable;

    /** 智能合约部署信息 */
    private Map<String, ContractConfig> contracts;
}
