package com.practice.bom.entity;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @author ljf
 * @description 回调参数
 * @date 2023/1/12 4:46 PM
 */
@Data
public class CallBackData {

    private String successJson;

    private String failJson;

    Consumer<String> successConsumer;

    Consumer<String> failConsumer;

}
