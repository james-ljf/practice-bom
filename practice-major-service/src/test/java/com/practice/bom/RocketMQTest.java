package com.practice.bom;

import cn.hutool.core.util.IdUtil;
import com.practice.bom.constants.RocketMqConstants;
import com.practice.bom.entity.Notice;
import com.practice.bom.util.RocketMqHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ljf
 * @description rocketmq测试
 * @date 2023/1/30 5:42 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketMQTest {

    @Resource
    private RocketMqHelper rocketMqService;

    @Test
    public void testAsyncSend() {
        Notice notice = new Notice();
        notice.setId(IdUtil.getSnowflakeNextIdStr());
        notice.setName("这是一个rocketmq通知");
        notice.setDescription("恭喜你，成为资深的JAVA程序员菜鸡。");
        notice.setHasRead(1);
//        rocketMqService.convertAndSend(RocketMqConstants.PRODUCER_GROUP, JSON.toJSONString(notice));
//        rocketMqService.asyncSend(RocketMqConstants.PRODUCER_GROUP, MessageBuilder.withPayload(notice).build());
        SendResult sendResult = rocketMqService.send(RocketMqConstants.TEST_TOPIC, "_test", notice);
        Assert.assertNotNull(sendResult);
    }

    @Test
    public void testBatchSend() {
        Notice notice;
        for (int i = 0; i < 20000; i++) {
            notice = new Notice();
            notice.setId(IdUtil.getSnowflakeNextIdStr());
            notice.setName("这是一个rocketmq通知" + i);
            notice.setDescription("恭喜你，成为资深的JAVA程序员菜鸡。");
            notice.setHasRead(i);
            rocketMqService.asyncSend(RocketMqConstants.TEST_TOPIC, MessageBuilder.withPayload(notice).build());
        }
    }

    @Test
    public void testTransactionSend() {
        Notice notice = new Notice();
        notice.setId(IdUtil.getSnowflakeNextIdStr());
        notice.setName("这是一个rocketmq通知");
        notice.setDescription("恭喜你，成为资深的JAVA程序员菜鸡。");
        notice.setHasRead(1);
        rocketMqService.sendTransaction(RocketMqConstants.TEST_TOPIC, "_test_transaction", notice);
    }

}
