package com.hengxunda.generalservice.service;

import java.util.concurrent.TimeUnit;

public interface IStringRedisService {

    void set(String key, String value);
    void set(String key, String value, long timeOut, TimeUnit timeUnit) ;
    void expire(String key, long timeOut, TimeUnit timeUnit);
    String get(String key);
    void delete(String key);
    //递增1
    long incr(String key);
    //递减1
    long decr(String key);

    //递增 value
    long incrby(String key,long value);
    //递减 value
    long decrby(String key,long value);

}
