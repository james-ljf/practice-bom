package com.practice.bom.rest_template;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ljf
 * @description 通过请求测试RestTemplate
 * @date 2023/2/8 2:27 PM
 */
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final RestTemplate restTemplate;

    @RequestMapping(value = "/init-on")
    public String initOn() {
        return restTemplate.getForObject("http://practice-user-service/test-rest", String.class);
    }

    @RequestMapping(value = "/test-rest")
    public String testRest() {
        return "test success";
    }

}
