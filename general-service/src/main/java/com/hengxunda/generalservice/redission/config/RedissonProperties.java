package com.hengxunda.generalservice.redission.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    private int timeout = 3000;

    private String address;

    private String password;

    private int database = 15;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

}