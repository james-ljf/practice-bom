package com.practice.bom.pub_sub.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author ljf
 * @description 定义微博发布事件
 * @date 2023/2/7 4:23 PM
 */
public class WeiboPubEvent extends ApplicationEvent {

    public WeiboPubEvent(Object source) {
        super(source);
    }

}
