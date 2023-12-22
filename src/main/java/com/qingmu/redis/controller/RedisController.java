package com.qingmu.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingmu.redis.MultiRedisLettuceConnectionFactory;
import com.qingmu.redis.MultiRedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MultiRedisLettuceConnectionFactory factory;

    @Autowired
    private MultiRedisProperties multiRedisProperties;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/test")
    public Object test(@RequestParam("param") String currentRedis,
                       @RequestParam("key") String key,
                       @RequestParam("value") String value) throws JsonProcessingException {
        String s = objectMapper.writeValueAsString(multiRedisProperties);
        System.out.println(s);

        factory.setCurrentRedis(currentRedis);

        redisTemplate.opsForValue().set(key,value);
        Object o = redisTemplate.opsForValue().get(key);
        System.out.println(o);
        return o;
    }
}
