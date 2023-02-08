package com.practice.bom.rest_template.config;

import com.practice.bom.rest_template.annotation.MyLoadBalanced;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * 获取到注解了@MyLoadBalanced的RestTemplate集合，
 * 然后利用Spring扩展点SmartInitializingSingleton在所有Bean初始化之后添加拦截器，只调用一次。
 *
 * @author ljf
 * @description RestTemplate配置
 * @date 2023/2/8 2:19 PM
 */
@Configuration
public class RestConfig {

    @Autowired(required = false)
    @MyLoadBalanced
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Bean
    public SmartInitializingSingleton initLoadBalancedRestTemplate() {
        return () -> {
            // 遍历所有RestTemplate注入拦截器
            for (RestTemplate restTemplate : restTemplates) {
                restTemplate.getInterceptors().add(new MyClientHttpRequestInterceptor());
            }
        };
    }


}
