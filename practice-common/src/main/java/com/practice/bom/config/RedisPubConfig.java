package com.practice.bom.config;

import com.practice.bom.listener.RedisPubListener;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

/**
 * @author ljf
 * @description
 * @date 2023/2/6 2:20 PM
 */
@RequiredArgsConstructor
@Configuration
public class RedisPubConfig {

    private final List<RedisPubListener> redisMsgPubSubListenerList;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        if (redisMsgPubSubListenerList == null || redisMsgPubSubListenerList.isEmpty()) {
            return container;
        }
        for (RedisPubListener redisPubListener : redisMsgPubSubListenerList) {
            if (redisPubListener == null || StringUtils.isBlank(redisPubListener.getTopic())) {
                continue;
            }
            // 一个订阅者对应一个主题通道信息
            container.addMessageListener(redisPubListener, new PatternTopic(redisPubListener.getTopic()));
        }
        return container;
    }

}
