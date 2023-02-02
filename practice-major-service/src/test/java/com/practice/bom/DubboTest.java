package com.practice.bom;

import com.practice.bom.model.UserNoticeDTO;
import com.practice.bom.service.DemoProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ljf
 * @description
 * @date 2023/2/2 3:43 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboTest {

    @DubboReference
    private DemoProcessService demoProcessService;

    @Test
    public void testRpc() {
        UserNoticeDTO userNoticeDTO = demoProcessService.getUserNoticeDetail();
        Assert.assertNotNull(userNoticeDTO);
        log.info("[testRpc ok] : res = {}", userNoticeDTO);
    }

}
