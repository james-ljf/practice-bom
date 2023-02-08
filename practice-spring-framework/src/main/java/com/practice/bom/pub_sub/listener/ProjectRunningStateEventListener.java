package com.practice.bom.pub_sub.listener;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description 监听项目运行状态
 * @date 2023/2/8 11:14 AM
 */
@Slf4j
@Component
public class ProjectRunningStateEventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(@NotNull ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            log.info("初始化完成和刷新");
        } else if (event instanceof ContextStartedEvent) {
            log.info("应用启动，需要在代码动态添加监听器才可捕获 ");
        } else if (event instanceof ContextStoppedEvent) {
            log.info("应用停止 ");
        } else if (event instanceof ContextClosedEvent) {
            log.info("应用关闭 ");
        }
    }

}
