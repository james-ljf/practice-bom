package com.practice.bom.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description 消息过滤器
 * @date 2023/1/9 4:40 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerFilter {

   private final ConsumerFactory<String, String> consumerFactory;

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, String> filterContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory);
      // 丢弃被过滤的消息
      factory.setAckDiscarded(true);
      factory.setRecordFilterStrategy(consumerRecord -> {
         log.info("[KafkaConsumerFilter on] : 进行消息过滤。消息 = {}", consumerRecord);
         return false;
      });
      return factory;
   }

}
