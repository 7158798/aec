package com.hengxunda.wallet.btc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@SuppressWarnings("ALL")
@Configuration
public class Config {

    @Autowired
    private Environment env;

    @Bean
    public BTCHelper btcHelper() {
        String username = env.getProperty("btc.username");
        String password = env.getProperty("btc.password");
        String host = env.getProperty("btc.host");
        String port = env.getProperty("btc.port");
        return new BTCHelper(username, password, host, port);
    }

    @Bean
    public LTCHelper ltcHelper() {
        String username = env.getProperty("ltc.username");
        String password = env.getProperty("ltc.password");
        String host = env.getProperty("ltc.host");
        String port = env.getProperty("ltc.port");
        return new LTCHelper(username, password, host, port);
    }



}
