package com.practice.bom.pub_sub.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * 当Spring处理用户请求结束后，系统会自动触发该事件，可记录请求的信息
 *
 * @author ljf
 * @description 请求处理监听
 * @date 2023/2/8 10:49 AM
 */
@Slf4j
@Component
public class ServletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent> {

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        log.info("请求相关信息={}，访问路径={}，响应耗时={}，创建时间={}，失败原因异常={}，HTTP状态值={}", event.getDescription(),
                event.getRequestUrl(), event.getProcessingTimeMillis(), event.getTimestamp(), event.getFailureCause(), event.getStatusCode());
    }

}
