package com.practice.bom.rest_template.request;

import com.practice.bom.util.HostUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ljf
 * @description 实现HttpRequest重新构建请求
 * @date 2023/2/8 11:52 AM
 */
@Slf4j
public class MyRequest implements HttpRequest {

    private final HttpRequest sourceRequest;

    public MyRequest(HttpRequest sourceRequest) {
        this.sourceRequest = sourceRequest;
    }

    @NotNull
    @Override
    public String getMethodValue() {
        return this.sourceRequest.getMethodValue();
    }

    @NotNull
    @Override
    public HttpHeaders getHeaders() {
        return this.sourceRequest.getHeaders();
    }

    @Override
    public URI getURI() {
        URI sourceUri = sourceRequest.getURI();
        String url = HostUtil.getHost(sourceUri.getHost());
        try {
            URI newUri = new URI(sourceUri.getScheme() + "://" + url + sourceUri.getPath());
            log.info("新的uri = {}", newUri);
            return newUri;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return sourceUri;
    }



}
