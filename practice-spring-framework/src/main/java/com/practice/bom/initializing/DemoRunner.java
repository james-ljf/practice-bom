package com.practice.bom.initializing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化操作，ApplicationRunner会比CommandLineRunner先执行
 *
 * @author ljf
 * @description DemoRunner.class
 * @date 2023/2/8 10:29 AM
 */
@Slf4j
@Component
public class DemoRunner implements ApplicationRunner, CommandLineRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("我是ApplicationRunner.run()");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("我是CommandLineRunner.run()");
    }
}
