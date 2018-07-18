package com.hengxunda.task.huobi.ws.impl;

import com.hengxunda.task.huobi.ws.IWebSocketService;
import com.hengxunda.task.huobi.ws.Topic;
import com.hengxunda.task.huobi.ws.WebSocketBase;

public class WebSocketClient extends WebSocketBase {
	public WebSocketClient(String url,IWebSocketService service){
		super(url,service);
	}
	
	public void topicAll(String symbol){
		for (int i = 0; i < Topic.PERIOD.length; i++) {
			this.addSub(String.format(Topic.KLINE_SUB, symbol,Topic.PERIOD[i]), "client1");
		}

		this.addSub(String.format(Topic.DEPTH_SUB, symbol), "client1");
		this.addSub(String.format(Topic.TRADE_SUB, symbol), "client1");
		
	}
}
