package com.practice.bom.action.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.bom.constants.GlobalConstants;
import com.practice.bom.constants.KafkaTopicsConstants;
import com.practice.bom.entity.NoticeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description kafka监听消息
 * @date 2023/1/5 5:25 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageNotice {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {KafkaTopicsConstants.TOPIC_TEST}, groupId = GlobalConstants.DEFAULT_GROUP_ID)
    public void monitorMessage(ConsumerRecord<String, String> consumerRecord) {
        String msg = consumerRecord.value();
        log.info("成功接收消息：{}", msg);
    }

    @KafkaListener(topics = {KafkaTopicsConstants.TOPIC_NOTICE}, groupId = GlobalConstants.DEFAULT_GROUP_ID,
            containerFactory = "filterContainerFactory", errorHandler = "globalListenerErrorHandler")
    public void monitorNoticeMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        String msg = consumerRecord.value();
        log.info("成功接收消息：{}", msg);
        NoticeVO noticeVO = objectMapper.readValue(msg, NoticeVO.class);
        log.info("成功转换消息为对象：obj = {}", noticeVO);
    }

}
