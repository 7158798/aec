package com.hengxunda.generalservice.service.impl;

import com.hengxunda.generalservice.service.IStringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class StringRedisServiceImpl implements IStringRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeOut, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
    }

    public void expire(String key, long timeOut, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, timeOut, timeUnit);
    }


    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public long incr(String key) {
        return stringRedisTemplate.boundValueOps(key).increment(1);
    }

    @Override
    public long decr(String key) {
        return stringRedisTemplate.boundValueOps(key).increment(-1);
    }

    @Override
    public long incrby(String key, long value) {
         return stringRedisTemplate.boundValueOps(key).increment(Math.abs(value));
    }

    @Override
    public long decrby(String key, long value) {
         return stringRedisTemplate.boundValueOps(key).increment(-Math.abs(value));
    }
}
