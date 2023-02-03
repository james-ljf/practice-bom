package com.practice.bom.action.notice;

import com.practice.bom.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description redis通知接收
 * @date 2023/2/3 4:05 PM
 */
@Slf4j
@Component
public class RedisMessageNotice {

    public void subRedisMessage(Notice notice) {
        log.info("[接收到通知] : message = {}", notice);
    }

}
