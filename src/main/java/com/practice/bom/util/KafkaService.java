package com.practice.bom.util;

import com.practice.bom.entity.CallBackData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ljf
 * @description Kafka工具组件
 * @date 2023/1/5 4:54 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送消息
     * @param topic 主题
     * @param jsonMsg 消息json
     */
    public void send(String topic, String jsonMsg) {
        kafkaTemplate.send(topic, jsonMsg);
    }

    /**
     * 发送消息，返回回滚信息
     * @param topic 主题
     * @param jsonMsg 消息json
     */
    public void sendCallBack(String topic, String jsonMsg, CallBackData callBackData) {
        kafkaTemplate.send(topic, jsonMsg)
                .addCallback(
                        success -> callBackData.getSuccessConsumer().accept(callBackData.getSuccessJson()),
                        fail -> callBackData.getFailConsumer().accept(callBackData.getFailJson())
                );
    }

}
