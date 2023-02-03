package com.practice.bom;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.practice.bom.constants.RedisTopicConstants;
import com.practice.bom.entity.Notice;
import com.practice.bom.service.RedisPubService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ljf
 * @description
 * @date 2023/2/3 4:25 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPubSubTest {

    @Resource
    private RedisPubService redisPubService;

    @Test
    public void testPubMessage() {
        Notice notice = new Notice();
        notice.setId(IdUtil.getSnowflakeNextIdStr());
        notice.setName("来自星星的中奖通知");
        notice.setDescription("恭喜你，成为资深的JAVA程序员菜鸡。");
        notice.setHasRead(99);
        redisPubService.pubMessage(RedisTopicConstants.TEST_PUB_SUB.getTopic(), JSON.toJSONString(notice));
    }

}
