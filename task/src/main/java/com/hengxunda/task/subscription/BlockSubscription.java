package com.hengxunda.task.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import rx.Subscription;

/**
 * 以太坊区块链(Block)事件订阅
 * Created by jerry on 2018/1/24.
 */
/*@ConditionalOnBean(Web3j.class)
@Component*/
public class BlockSubscription {
    private static Logger logger = LoggerFactory.getLogger(BlockSubscription.class);

    @Autowired
    public BlockSubscription(Web3j web3j) {
        Subscription blockSubscription = web3j.blockObservable(false).subscribe(this::log);
    }

    private void log(EthBlock ethBlock) {
        EthBlock.Block block = ethBlock.getBlock();
        logger.info("新的区块[blockNumber:{}, transactions count:{}]", block.getNumber(), block.getTransactions().size());
    }
}
