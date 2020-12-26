package cn.edu.zucc.se2020g11.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author nonlinearthink
 */
@Service
@EnableAsync
public class RedisMessageMqService {

    RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public RedisMessageMqService(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Async
    public void push(String topic, Serializable content){
        redisTemplate.opsForList().rightPush(topic, content);
    }

    public Serializable pop(String topic){
        return redisTemplate.opsForList().leftPop(topic);
    }

}
