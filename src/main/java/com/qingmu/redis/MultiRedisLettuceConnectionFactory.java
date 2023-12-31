package com.qingmu.redis;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class MultiRedisLettuceConnectionFactory
        implements InitializingBean, DisposableBean, RedisConnectionFactory, ReactiveRedisConnectionFactory {

    private final Map<String, LettuceConnectionFactory> connectionFactoryMap;

    private static final ThreadLocal<String> currentRedis = new ThreadLocal<>();

    public MultiRedisLettuceConnectionFactory(Map<String, LettuceConnectionFactory> connectionFactoryMap) {
        this.connectionFactoryMap = connectionFactoryMap;
    }

    public void setCurrentRedis(String currentRedis) {
        if (!connectionFactoryMap.containsKey(currentRedis)) {
            throw new RedisRelatedException("invalid currentRedis: " + currentRedis + ", it does not exists in configuration");
        }
        MultiRedisLettuceConnectionFactory.currentRedis.set(currentRedis);
    }

    @Override
    public void destroy() throws Exception {
        connectionFactoryMap.values().forEach(LettuceConnectionFactory::destroy);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        connectionFactoryMap.values().forEach(LettuceConnectionFactory::afterPropertiesSet);
    }

    private LettuceConnectionFactory currentLettuceConnectionFactory() {
        String currentRedis = MultiRedisLettuceConnectionFactory.currentRedis.get();
        if (!StringUtils.isEmpty(currentRedis)) {
            return connectionFactoryMap.get(currentRedis);
        }
        return connectionFactoryMap.get(MultiRedisProperties.DEFAULT);
    }

    @Override
    public ReactiveRedisConnection getReactiveConnection() {
        return currentLettuceConnectionFactory().getReactiveConnection();
    }

    @Override
    public ReactiveRedisClusterConnection getReactiveClusterConnection() {
        return currentLettuceConnectionFactory().getReactiveClusterConnection();
    }

    @Override
    public RedisConnection getConnection() {
        return currentLettuceConnectionFactory().getConnection();
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return currentLettuceConnectionFactory().getClusterConnection();
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return currentLettuceConnectionFactory().getConvertPipelineAndTxResults();
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return currentLettuceConnectionFactory().getSentinelConnection();
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return currentLettuceConnectionFactory().translateExceptionIfPossible(ex);
    }
}
