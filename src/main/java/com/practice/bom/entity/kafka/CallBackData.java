package com.practice.bom.entity.kafka;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @author ljf
 * @description Kafka回调实体
 * @date 2023/1/12 4:46 PM
 */
@Data
public class CallBackData {

    /**
     * 成功的参数
     */
    private String successJson;

    /**
     * 失败的参数
     */
    private String failJson;

    /**
     * 成功的回调方法
     */
    Consumer<String> successConsumer;

    /**
     * 失败的回调方法
     */
    Consumer<String> failConsumer;

}
