package com.practice.bom.pub_sub.listener;

import com.practice.bom.pub_sub.event.WeiboPubEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description 发布微博监听器
 * @date 2023/2/7 4:25 PM
 */
@Slf4j
@Component
public class WeiboPubListener implements ApplicationListener<WeiboPubEvent> {

    @Override
    public void onApplicationEvent(WeiboPubEvent event) {
        log.info("粉丝收到事件信息：{}", event.getSource());
        log.info("已通知粉丝你发布了一条微博。");
    }

}
