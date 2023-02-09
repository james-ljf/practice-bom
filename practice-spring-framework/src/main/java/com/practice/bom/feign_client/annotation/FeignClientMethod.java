package com.practice.bom.feign_client.annotation;

import org.springframework.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljf
 * @description feign请求方法注解，用于指定请求的路径与类型
 * @date 2023/2/9 3:23 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignClientMethod {

    /**
     * @return feign请求路径
     */
    String path();

    HttpMethod method() default HttpMethod.POST;

}
