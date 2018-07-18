package com.hengxunda.generalservice.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix="spring.redis")
public class RedisPropertiesConfig {

    private String host;
    private int port;
    private String password;
    private int timeout;
    private Jedis jedis;

    @Data
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class Jedis {
        private Pool pool;
    }

    @Data
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class Pool{
        private int maxActive;
        private int maxIdle;
    }


}
