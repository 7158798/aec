package com.hengxunda.task;

import com.hengxunda.task.huobi.ws.IWebSocketService;
import com.hengxunda.task.huobi.ws.impl.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 查询火币网接口
 */
@Component
public class CoinRateWsTask implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    String url = "wss://api.huobi.pro/ws";

    @Autowired
    private IWebSocketService iWebSocketService;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("================开始执行查询币币汇率任务==================");

        WebSocketClient client = new WebSocketClient(url, iWebSocketService);
        client.start();
        client.addSub("market.btcusdt.detail", "id11");
        client.addSub("market.ltcusdt.detail", "id12");

        logger.info("================结束执行查询币币汇率任务==================");
    }
}
