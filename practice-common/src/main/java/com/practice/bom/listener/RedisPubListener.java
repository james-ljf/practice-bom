package com.practice.bom.listener;

import org.springframework.data.redis.connection.MessageListener;

/**
 * @author ljf
 * @description redis发布/订阅监听接口，订阅者需实现该接口以完成订阅
 * @date 2022/12/30 4:30 PM
 */
public interface RedisPubListener extends MessageListener {

    /**
     * 获取通道主题
     *
     * @return text
     */
    String getTopic();

    /**
     * 获取类型
     *
     * @return text
     */
    default String getType() {
        return this.getClass().getSimpleName();
    }
}
