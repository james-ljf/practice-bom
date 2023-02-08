package com.practice.bom.pub_sub;

import com.practice.bom.pub_sub.event.WeiboPubEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author ljf
 * @description 发布订阅模式测试
 * @date 2023/2/7 4:30 PM
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PubSubTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void testPubWeibo() {
        applicationContext.publishEvent(new WeiboPubEvent("今天阳光真好！"));
    }


}
