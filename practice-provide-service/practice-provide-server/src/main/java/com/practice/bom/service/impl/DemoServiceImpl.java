package com.practice.bom.service.impl;

import cn.hutool.core.util.IdUtil;
import com.practice.bom.entity.NoticeBO;
import com.practice.bom.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ljf
 * @description
 * @date 2023/2/2 3:24 PM
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public NoticeBO getNotice() {
        NoticeBO noticeBO = new NoticeBO();
        noticeBO.setNoticeId(IdUtil.getSnowflakeNextIdStr());
        noticeBO.setHasRead(1);
        noticeBO.setName("这是一个正经的通知");
        noticeBO.setDescription("测试一下dubbo3");
        return noticeBO;
    }

    @Override
    public String getUserId() {
        return IdUtil.nanoId();
    }
}
