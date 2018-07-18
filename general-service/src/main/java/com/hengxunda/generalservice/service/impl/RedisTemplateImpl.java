package com.hengxunda.generalservice.service.impl;

import com.hengxunda.generalservice.service.IRedisTemplateSerivice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

@Slf4j
@Service
public class RedisTemplateImpl implements IRedisTemplateSerivice {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    public String lock(String key,String value,int time){
        Jedis jedis = getJedis();
        log.info("==>jedis{}<==",jedis);
        log.info("==>jedis set start<==");
        log.info("==>jedis set key={}<==",key);
        log.info("==>jedis set value={}<==",value);
        String result = jedis.set(key,value,"NX","PX",time);
        log.info("==>jedis set end<==");
        return result;
    }

    public Object unlock(String key,String value) {
        Jedis jedis = getJedis();
        String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        return result;
    }

}
