package com.practice.bom.service.dubbo;

import com.practice.bom.entity.NoticeBO;
import com.practice.bom.model.UserNoticeDTO;
import com.practice.bom.service.DemoProcessService;
import com.practice.bom.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author ljf
 * @description
 * @date 2023/2/2 3:38 PM
 */
@Slf4j
@DubboService
@RequiredArgsConstructor
public class DemoProcessServiceImpl implements DemoProcessService {

    private final DemoService demoService;

    @Override
    public UserNoticeDTO getUserNoticeDetail() {
        // 获取最后一次调用的提供方IP地址
        String serverIp = RpcContext.getServiceContext().getRemoteHost();
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        String application = RpcContext.getServiceContext().getUrl().getParameter("application");
        log.info("[get rpc context info ok] ：serverIP = {}，application = {}", serverIp, application);
        NoticeBO noticeBO = demoService.getNotice();
        String userId = demoService.getUserId();
        UserNoticeDTO userNoticeDTO = new UserNoticeDTO();
        userNoticeDTO.setNoticeId(noticeBO.getNoticeId());
        userNoticeDTO.setUserId(userId);
        userNoticeDTO.setName(noticeBO.getName());
        userNoticeDTO.setDescription(noticeBO.getDescription());
        userNoticeDTO.setHasRead(noticeBO.getHasRead());
        return userNoticeDTO;
    }

}
