package com.practice.bom.feign_client.config;


import com.practice.bom.feign_client.annotation.EnableFeignClients;
import com.practice.bom.feign_client.annotation.MyFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author ljf
 * @description
 * @date 2023/2/9 4:00 PM
 */
@Slf4j
public class FeignClientsRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry) {
        // 获取到EnableFeignClients注解上的属性
        Map<String, Object> annotations = importingClassMetadata.getAnnotationAttributes(EnableFeignClients.class.getName());
        if (annotations == null || annotations.size() == 0) {
            return;
        }
        log.info("[registerBeanDefinitions.annotations]: {}", annotations);
        // 获取需要扫描的包的路径
        String basePackage = String.valueOf(annotations.get("basePackage"));
        log.info("[registerBeanDefinitions.basePackage] : {}", basePackage);
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
            /**
             * 判断资源是否为候选组件，此处直接返回true
             * 底层实现会通过excludeFilters和includeFilters进行判断。
             *
             * @param beanDefinition bean资源
             * @return true
             */
            @Override
            protected boolean isCandidateComponent(@NotNull AnnotatedBeanDefinition beanDefinition) {
                return true;
            }
        };
        // 扫描注解MyFeignClient的类
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyFeignClient.class));
        // 根据包路径查找候选的组件
        Set<BeanDefinition> beanDefinitionSet = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition beanDefinition : beanDefinitionSet) {
            // 向构造方法中添加目标bean的全路径名，Spring会自动转换成Class对象
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(Objects.requireNonNull(beanDefinition.getBeanClassName()));
            beanDefinition.setBeanClassName(FeignFactoryBean.class.getName());
            registry.registerBeanDefinition(Objects.requireNonNull(beanDefinition.getBeanClassName()), beanDefinition);
        }
    }
}
