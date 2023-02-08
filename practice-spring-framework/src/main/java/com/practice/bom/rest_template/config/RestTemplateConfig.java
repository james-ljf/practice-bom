package com.practice.bom.rest_template.config;

import com.practice.bom.rest_template.annotation.MyLoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ljf
 * @description
 * @date 2023/2/8 3:13 PM
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @MyLoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
