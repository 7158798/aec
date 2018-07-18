package com.hengxunda.generalservice.service;

public interface IRedisTemplateSerivice {

    String lock(String key,String value,int time);

    Object unlock(String key,String value);
}
