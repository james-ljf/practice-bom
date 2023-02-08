package com.practice.bom.pub_sub.listener;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 结合BeanPostProcessor收集自定义注解，将收集到的接口信息提交到其他服务
 * 可用作服务信息上报
 *
 * @author ljf
 * @description
 * @date 2023/2/8 10:55 AM
 */
@Slf4j
@Component
public class ReportServiceEventListener implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    private Set<Object> serviceInstances = new HashSet<>();

    /**
     * 防止调用超过一次
     */
    private boolean isExecute = false;

    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RestController.class)) {
            log.info("Component Bean : {}", beanName);
            serviceInstances.add(bean);
        }
        return bean;
    }

    /**
     * ApplicationContext 被初始化或刷新时，ContextRefreshedEvent事件被发布。
     * 这也可以在 ConfigurableApplicationContext接口中使用 refresh() 方法来发生。
     * 此处的初始化是指：所有的Bean被成功装载，后处理Bean被检测并激活，
     * 所有单例Bean被预实例化，ApplicationContext容器已就绪可用。
     *
     * @param event 事件
     */
    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        if (isExecute) {
            return;
        }
        List<String> infoList = new ArrayList<>();
        for (Object bean : serviceInstances) {
            if (bean == null) {
                continue;
            }
            RestController controller = AnnotationUtils.findAnnotation(bean.getClass(), RestController.class);
            if (controller != null) {
                // 这里可获取需要的信息
                infoList.add(controller.value());
            }
        }
        // 这里可进行服务信息上报逻辑
        log.info("infoList : {}", infoList);
    }

}
