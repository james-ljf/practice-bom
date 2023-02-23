package com.practice.bom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description
 * @date 2023/2/13 5:22 PM
 */
@Component
@ConfigurationProperties(prefix = "practice.netty")
public class NettyConfig {

    /**
     * 解决粘包拆包
     */
    public static final String DATA_PACK_SEPARATOR = "#$&*";

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
