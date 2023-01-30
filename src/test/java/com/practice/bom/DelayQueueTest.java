package com.practice.bom;

import cn.hutool.core.util.IdUtil;
import com.practice.bom.entity.DelayBase;
import com.practice.bom.entity.Order;
import com.practice.bom.manger.OrderDelayQueueManger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author ljf
 * @description
 * @date 2023/1/29 2:29 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DelayQueueTest {

    @InjectMocks
    private OrderDelayQueueManger orderDelayQueueManger;

    @Before
    public void before() throws InterruptedException {
        DelayBase<Order> delayBase;
        Order order;
        for (int i = 0; i < 2; i++) {
            String id = IdUtil.getSnowflakeNextIdStr();
            LocalDateTime nowTime = LocalDateTime.now();
            delayBase = new DelayBase<>();
            order = new Order();
            order.setId(IdUtil.getSnowflakeNextIdStr());
            order.setName("测试名称" + i);
            delayBase.setData(order);
            delayBase.setCreatedTime(nowTime);
            delayBase.setId(id);
            Thread.sleep(2000L);
            orderDelayQueueManger.offer(delayBase);
        }
    }

    @Test
    public void testDelayQueue() throws InterruptedException {
        DelayBase<Order> delayBase = new DelayBase<>();
        Order order = new Order();
        String id = IdUtil.getSnowflakeNextIdStr();
        LocalDateTime nowTime = LocalDateTime.now();
        order.setId(IdUtil.getSnowflakeNextIdStr());
        order.setName("羊羔绒外套");
        delayBase.setData(order);
        delayBase.setCreatedTime(nowTime);
        delayBase.setId(id);
        delayBase.setExpireVal(10000L);
        Thread.sleep(3000L);
        orderDelayQueueManger.offer(delayBase);
        orderDelayQueueManger.execute();
    }

}
