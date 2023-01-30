package com.practice.bom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ljf
 * @description 线程池配置
 * @date 2023/1/30 9:36 AM
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean("delayExecutorService")
    public ThreadPoolTaskExecutor delayExecutorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(9999);
        executor.setThreadNamePrefix("delay-executor-");
        // 拒绝策略：达到maxSize后，不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
