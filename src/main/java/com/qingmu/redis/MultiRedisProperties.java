package com.qingmu.redis;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class MultiRedisProperties {
    /**
     * 默认连接必须配置，配置 key 为 default
     */
    public static final String DEFAULT = "default";

    private Map<String, RedisProperties> multi;
}
