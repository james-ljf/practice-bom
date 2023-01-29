package com.practice.bom.config;

import com.practice.bom.constants.KafkaTopicsConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author ljf
 * @description
 * @date 2023/1/5 4:41 PM
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean
    public NewTopic topicTest() {
        return TopicBuilder.name(KafkaTopicsConstants.TOPIC_TEST)
                // 分区数
                .partitions(2)
                // 副本数
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topicNotice() {
        return TopicBuilder.name(KafkaTopicsConstants.TOPIC_NOTICE)
                .partitions(2)
                .replicas(2)
                .build();
    }

}
