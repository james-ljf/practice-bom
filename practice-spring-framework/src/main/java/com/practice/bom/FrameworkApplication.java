package com.practice.bom;

import com.practice.bom.feign_client.annotation.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ljf
 * @description
 * @date 2023/2/7 2:14 PM
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackage = "com.practice.bom.feign_client.feign")
@SpringBootApplication
public class FrameworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrameworkApplication.class, args);
    }
}