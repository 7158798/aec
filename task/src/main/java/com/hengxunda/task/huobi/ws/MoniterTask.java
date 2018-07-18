package com.hengxunda.task.huobi.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class MoniterTask  extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(MoniterTask.class);
	private long startTime = System.currentTimeMillis();
	private int checkTime = 5000;
	private WebSocketBase client = null;

	public void updateTime() {
//		logger.info("startTime is update");
		startTime = System.currentTimeMillis();
	}

	public MoniterTask(WebSocketBase client) {
		this.client = client;
		logger.info("TimerTask is starting.... ");
	}

	public void run() {
		if (System.currentTimeMillis() - startTime > checkTime) {
			client.setStatus(false);
			logger.info("Moniter reconnect....... ");
			client.reConnect();
		}
		 
		
	}
}