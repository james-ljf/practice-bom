package com.practice.bom.factory_bean.config;

import com.practice.bom.factory_bean.service.AlgorithmFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册AlgorithmFactoryBean，确定使用的是哪个算法
 *
 * @author ljf
 * @description 算法接口配置
 * @date 2023/2/7 2:53 PM
 */
@Configuration
public class AlgorithmConfig {

    @Bean
    public AlgorithmFactoryBean algorithmFactoryBean() {
        AlgorithmFactoryBean algorithmFactoryBean = new AlgorithmFactoryBean();
        algorithmFactoryBean.setAlgorithmType("MD5");
        return algorithmFactoryBean;
    }

}
