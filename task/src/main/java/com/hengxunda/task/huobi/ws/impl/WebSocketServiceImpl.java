package com.hengxunda.task.huobi.ws.impl;

import com.alibaba.fastjson.JSONObject;
import com.hengxunda.common.Enum.TransPairEnum;
import com.hengxunda.common.entity.CoinRateEntity;
import com.hengxunda.common.entity.CoinRateList;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.task.huobi.ws.IWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class WebSocketServiceImpl implements IWebSocketService {

	@Autowired
	private IStringRedisService iStringRedisService;

	public void onReceive(JSONObject json) {
//		log.info("receive:" + json.toJSONString());
		CoinRateList coinRateList = json.toJavaObject(CoinRateList.class);
		if(Objects.nonNull(coinRateList)&&Objects.nonNull(coinRateList.getTick())){
			CoinRateEntity coinRateEntity = coinRateList.getTick();
			String money = Objects.isNull(coinRateEntity)? "" : (StringUtils.isBlank(coinRateEntity.getClose().toString()) ? "" : coinRateEntity.getClose().toString());
			if(StringUtils.equals("market.btcusdt.detail",coinRateList.getCh())){
					iStringRedisService.set(TransPairEnum.BTC2USDT.getCode(),money,10, TimeUnit.SECONDS);
			}else{
				iStringRedisService.set(TransPairEnum.LTC2USDT.getCode(),money,10, TimeUnit.SECONDS);
			}
		}
	}
	
	public void onReset(){
		
	}
}
