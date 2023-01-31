package com.practice.bom.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description rocketmq工具组件
 * @date 2023/1/30 5:15 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RocketMqService {

    private final RocketMQTemplate rocketMQTemplate;


    /**
     * 普通发送
     *
     * @param topic 消息主题
     * @param msg   消息体
     * @param <T>   消息泛型
     */
    public <T> void convertAndSend(String topic, T msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }


    /**
     * 发送带tag的消息，直接在topic后面加上":tag"
     *
     * @param topic 消息主题
     * @param tag   消息tag
     * @param msg   消息体
     * @param <T>   消息泛型
     */
    public <T> SendResult send(String topic, String tag, T msg) {
        topic = topic + ":" + tag;
        return this.send(topic, msg);
    }

    /**
     * 发送同步消息
     *
     * @param topic 消息主题
     * @param msg   消息体
     */
    public <T> SendResult send(String topic, T msg) {
        Message<T> message = MessageBuilder.withPayload(msg).build();
        return rocketMQTemplate.syncSend(topic, message);
    }

    /**
     * 发送顺序消息
     *
     * @param message 消息
     * @param topic   主题
     * @param hashKey 确定消息发送到哪个队列中
     * @param timeout 超时时间
     */
    public void sendOrderly(String topic, Message<?> message, String hashKey, long timeout) {
        rocketMQTemplate.syncSendOrderly(topic, message, hashKey, timeout);
    }

    /**
     * 发送异步消息
     *
     * @param topic   消息Topic
     * @param message 消息实体
     */
    public void asyncSend(Enum<?> topic, Message<?> message) {
        asyncSend(topic.name(), message, this.getDefaultSendCallBack());
    }


    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     */
    public void asyncSend(Enum<?> topic, Message<?> message, SendCallback sendCallback) {
        this.asyncSend(topic.name(), message, sendCallback);
    }

    /**
     * 发送异步消息
     *
     * @param topic   消息Topic
     * @param message 消息实体
     */
    public void asyncSend(String topic, Message<?> message) {
        this.asyncSend(topic, message, this.getDefaultSendCallBack());
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback);
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback, long timeout) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback, timeout);
    }

    /**
     * 发送异步消息
     *
     * @param topic        消息Topic
     * @param message      消息实体
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     * @param delayLevel   延迟消息的级别
     */
    public void asyncSend(String topic, Message<?> message, SendCallback sendCallback, long timeout, int delayLevel) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback, timeout, delayLevel);
    }

    /**
     * 默认CallBack函数
     *
     * @return SendCallback.class
     */
    private SendCallback getDefaultSendCallBack() {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("---send success---");
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("---send error---" + throwable.getMessage(), throwable.getMessage());
            }
        };
    }

}
