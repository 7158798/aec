package com.hengxunda.generalservice.redission.config;

import com.hengxunda.generalservice.redission.DistributedLocker;
import com.hengxunda.generalservice.redission.RedissLockUtil;
import com.hengxunda.generalservice.redission.RedissonDistributedLocker;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redssionProperties;



    @Bean
    @ConditionalOnProperty(name="redisson.address")
    public RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize())
                .setDatabase(redssionProperties.getDatabase());


        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }

        return Redisson.create(config);
    }


    @Bean
    public DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        RedissLockUtil.setLocker(locker);
        return locker;
    }

}