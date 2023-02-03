package com.practice.bom.config.rocketmq;

import io.netty.channel.DefaultChannelId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ljf
 * @description rocketmq配置
 * @date 2023/1/31 3:49 PM
 */
@Configuration
public class RocketMqConfiguration {

    /**
     * 通过spring去加载，能够避免启动时加载此bean超时
     */
    @Bean
    public DefaultChannelId newDefaultChannelId() {
        return DefaultChannelId.newInstance();
    }
}
