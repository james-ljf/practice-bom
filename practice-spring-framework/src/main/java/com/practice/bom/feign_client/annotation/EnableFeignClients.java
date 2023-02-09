package com.practice.bom.feign_client.annotation;

import com.practice.bom.feign_client.config.FeignClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljf
 * @description 通过 @EnableFeignClients引入FeignClientsRegistrar客户端注册类
 * @date 2023/2/9 3:18 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(FeignClientsRegistrar.class)
public @interface EnableFeignClients {

    /**
     * 扫描的包路径
     *
     * @return 包路径
     */
    String basePackage();

}
