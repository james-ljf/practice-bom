package com.practice.bom.constants;

/**
 * @author ljf
 * @description Redis 发布订阅主题
 * @date 2023/2/3 4:06 PM
 */
public enum RedisTopicConstants {

    /**
     * 测试发布/订阅模式
     */
    TEST_PUB_SUB("topic::test_pub_sub");

    private final String topic;

    RedisTopicConstants(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
