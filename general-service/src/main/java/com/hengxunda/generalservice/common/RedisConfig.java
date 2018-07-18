package com.hengxunda.generalservice.common;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    private static final int database=15;

    @Autowired
    private RedisPropertiesConfig config;

    @Bean
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(config.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMaxTotal(config.getJedis().getPool().getMaxActive());
        return jedisPoolConfig;
    }


    @Bean
    public JedisPool jedisPool(){
        JedisPool jedisPool = null;
        if (StringUtils.isBlank(config.getPassword())){
            jedisPool = new JedisPool(getRedisConfig(), config.getHost(), config.getPort(), config.getTimeout(),null,database);
        }else{
            jedisPool = new JedisPool(getRedisConfig(), config.getHost(), config.getPort(), config.getTimeout(),config.getPassword(),database);
        }
        return jedisPool;
    }

}
