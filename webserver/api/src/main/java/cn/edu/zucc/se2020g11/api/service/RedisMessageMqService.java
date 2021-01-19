package cn.edu.zucc.se2020g11.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Redis消息队列服务
 *
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

    /**
     * 加入队列
     *
     * @param topic 主题
     * @param content 内容
     */
    @Async
    public void push(String topic, Serializable content){
        redisTemplate.opsForList().rightPush(topic, content);
    }

    /**
     * 获取用户历史记录
     *
     * @param topic 主题
     * @return 序列化消息
     */
    public Serializable pop(String topic){
        return redisTemplate.opsForList().leftPop(topic);
    }

}
