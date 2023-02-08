package com.practice.bom.rest_template.config;

import com.practice.bom.rest_template.request.MyRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 自定义客户端请求拦截器
 *
 * @author ljf
 * @description
 * @date 2023/2/8 2:09 PM
 */
public class MyClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    /**
     * 构建新的请求并返回
     *
     * @param request   当前请求
     * @param body      请求体二进制信息
     * @param execution 客户端HTTP请求执行的上下文
     */
    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        MyRequest myRequest = new MyRequest(request);
        return execution.execute(myRequest, body);
    }

}
