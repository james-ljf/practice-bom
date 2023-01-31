package com.practice.bom.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description kafka消息发送结果回调处理
 * @date 2023/1/5 4:47 PM
 */
@Slf4j
@Component
public class KafkaSendCallbackHandler implements ProducerListener<Object, Object> {

    @Override
    public void onSuccess(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata) {
        String resultStr = buildResult(producerRecord);
        log.error("[kafka消息发送成功]: msg = {}", resultStr);
    }

    @Override
    public void onError(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata, Exception e) {
        String resultStr = buildResult(producerRecord);
        log.error("[kafka消息发送失败]: msg = {}, 可进行失败补偿", resultStr);
        e.printStackTrace();
    }

    private String buildResult(ProducerRecord<Object, Object> producerRecord) {
        String topic = producerRecord.topic();
        return "<topic>: " + topic + ",\n <value> :" + producerRecord.value();
    }
}
