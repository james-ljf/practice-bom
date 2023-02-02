package com.practice.bom;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.practice.bom.constants.KafkaTopicsConstants;
import com.practice.bom.entity.Notice;
import com.practice.bom.util.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ljf
 * @description
 * @date 2023/1/5 5:32 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    @Resource
    private KafkaService kafkaService;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testSend() {
        kafkaService.send(KafkaTopicsConstants.TOPIC_TEST, "我在发送呢，别急。");
    }

    @Test
    public void testSendNotice() {
        Notice notice = new Notice();
        notice.setId(IdUtil.getSnowflakeNextIdStr());
        notice.setName("来自星星的中奖通知");
        notice.setDescription("恭喜你，成为资深的JAVA程序员菜鸡。");
        notice.setHasRead(1);
        kafkaService.send(KafkaTopicsConstants.TOPIC_NOTICE, JSON.toJSONString(notice));
    }
}
