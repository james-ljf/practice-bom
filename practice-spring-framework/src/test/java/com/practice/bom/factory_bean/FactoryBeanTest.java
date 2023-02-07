package com.practice.bom.factory_bean;

import com.practice.bom.factory_bean.service.AlgorithmService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author ljf
 * @description
 * @date 2023/2/7 3:17 PM
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FactoryBeanTest {

    @Resource
    private AlgorithmService algorithmService;

    @Test
    public void testAlgorithmServiceEncrypt() {
        String res = algorithmService.encrypt("你猜这是什么算法");
        Assert.assertNotNull(res);
        log.info("算法是：{}", res);
    }

}
