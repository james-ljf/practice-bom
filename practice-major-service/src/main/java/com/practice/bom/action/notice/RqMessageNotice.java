package com.practice.bom.action.notice;

import com.practice.bom.constants.RocketMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description rocketMQ消息监听
 * @date 2023/1/30 5:54 PM
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMqConstants.TEST_TOPIC, consumerGroup = RocketMqConstants.CONSUMER_GROUP)
public class RqMessageNotice implements RocketMQListener<String> {


    @Override
    public void onMessage(String msg) {
        log.info("rocketMQ消息 ： {}", msg);
    }
}
