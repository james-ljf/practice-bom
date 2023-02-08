package com.practice.bom.application_context_aware;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 实现ApplicationContextAware接口编写静态方法工具类，可以在普通的类中获取到Spring IOC容器中的对象
 *
 * @author ljf
 * @description 静态方法工具类
 * @date 2023/2/8 9:48 AM
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


    public static <T> T getBean(Class<T> classType) {
        return context.getBean(classType);
    }

}
