package com.practice.bom.action;

import com.alibaba.fastjson.JSON;
import com.practice.bom.action.notice.RedisMessageNotice;
import com.practice.bom.constants.RedisTopicConstants;
import com.practice.bom.entity.Notice;
import com.practice.bom.listener.RedisPubListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ljf
 * @description redis订阅者监听消息
 * @date 2023/2/3 4:04 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageListener implements RedisPubListener {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisMessageNotice redisMessageNotice;

    @Override
    public String getTopic() {
        return RedisTopicConstants.TEST_PUB_SUB.getTopic();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        String jsonString = Objects.requireNonNull(serializer.deserialize(message.getBody())).toString();
        log.info("[RedisMessageListener.onMessage() on] : msg = {}", jsonString);
        Notice notice = JSON.parseObject(jsonString, Notice.class);
        redisMessageNotice.subRedisMessage(notice);
    }

}
