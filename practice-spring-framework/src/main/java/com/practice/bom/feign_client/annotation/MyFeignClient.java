package com.practice.bom.feign_client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljf
 * @description 主要用于FeignClient接口，进行扫描过滤
 * @date 2023/2/9 3:21 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFeignClient {

    String name();
}
