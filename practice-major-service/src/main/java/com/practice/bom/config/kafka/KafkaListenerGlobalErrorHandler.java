package com.practice.bom.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description kafka全局消息异常处理器
 * @date 2023/1/12 2:48 PM
 */
@Slf4j
@Component
public class KafkaListenerGlobalErrorHandler {

    @Bean
    public ConsumerAwareListenerErrorHandler globalListenerErrorHandler() {
        return (message, exception, consumer) -> {
            log.error("errMsg = {}", message);
            log.error("e = {}", exception.getMessage());
            return message;
        };
    }

}
