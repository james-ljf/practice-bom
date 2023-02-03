package com.practice.bom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ljf
 * @description
 * @date 2023/2/3 4:28 PM
 */
@Service
public class RedisPubServiceImpl implements RedisPubService {

    private final Logger logger;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPubServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.logger = LoggerFactory.getLogger(RedisPubServiceImpl.class);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void pubMessage(String topic, String message) {
        logger.info("[发布redis消息] : topic = {}, msg = {}", topic, message);
        redisTemplate.convertAndSend(topic, message);
    }
}
