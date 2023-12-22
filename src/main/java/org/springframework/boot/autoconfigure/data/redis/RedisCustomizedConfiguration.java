package org.springframework.boot.autoconfigure.data.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingmu.redis.MultiRedisLettuceConnectionFactory;
import com.qingmu.redis.MultiRedisProperties;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Administrator
 */
@Configuration
public class RedisCustomizedConfiguration {

    /**
     * @param builderCustomizers
     * @param clientResources
     * @param multiRedisProperties
     * @return
     * @see org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration
     */
    @Bean
    public MultiRedisLettuceConnectionFactory multiRedisLettuceConnectionFactory(
            ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ClientResources clientResources,
            MultiRedisProperties multiRedisProperties,
            ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider,
            ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider
    ) throws JsonProcessingException {
        Map<String, LettuceConnectionFactory> connectionFactoryMap =new HashMap<>();
        Map<String, RedisProperties> multi = multiRedisProperties.getMulti();

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(multi));
        multi.forEach((k, v) -> {
            LettuceConnectionConfiguration lettuceConnectionConfiguration = new LettuceConnectionConfiguration(
                    v,
                    sentinelConfigurationProvider,
                    clusterConfigurationProvider
            );
            LettuceConnectionFactory lettuceConnectionFactory = null;
            try {
                lettuceConnectionFactory = lettuceConnectionConfiguration.redisConnectionFactory(builderCustomizers, clientResources);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            connectionFactoryMap.put(k, lettuceConnectionFactory);
        });
        return new MultiRedisLettuceConnectionFactory(connectionFactoryMap);
    }

}
