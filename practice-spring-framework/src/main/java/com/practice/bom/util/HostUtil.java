package com.practice.bom.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * host工具类
 *
 * @author ljf
 * @description 根据不同的服务，获取对应的host.
 * @date 2023/2/8 11:53 AM
 */
@Slf4j
public class HostUtil {

    private HostUtil() {
    }

    private static final Random RANDOM = new Random();

    private static final Map<String, String[]> HOST_MAP = new HashMap<>();

    static {
        HOST_MAP.put("practice-user-service", new String[]{"127.0.0.1:8090", "localhost:8090"});
    }

    /**
     * 根据服务名获取一个host（简易版）
     *
     * @param serviceName 服务名称
     * @return host
     */
    public static String getHost(String serviceName) {
        String[] hosts = HOST_MAP.get(serviceName);
        if (hosts == null) {
            return "";
        }
        int num = hosts.length;
        int index = RANDOM.nextInt(num);
        String host = hosts[index];
        log.info("根据随机算法，当前获取到的host: {}", host);
        return host;
    }

}
