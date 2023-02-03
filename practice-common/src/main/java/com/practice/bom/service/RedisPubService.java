package com.practice.bom.service;

/**
 * @author ljf
 * @description reids发布消息接口
 * @date 2023/2/3 4:27 PM
 */
public interface RedisPubService {

    /**
     * 发布消息
     *
     * @param topic   主题
     * @param message 消息
     */
    void pubMessage(String topic, String message);

}
