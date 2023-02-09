package com.practice.bom.feign_client;

import com.practice.bom.feign_client.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljf
 * @description
 * @date 2023/2/9 5:00 PM
 */
@RestController
@RequiredArgsConstructor
public class FeignDemoController {

    private final UserFeignClient userFeignClient;

    @PostMapping(value = "/test-feign")
    public Object testFeign() {
        return "test-feign success！";
    }

    @GetMapping(value = "/feign-client-test-on")
    public Object feignClientTestOn() {
        return userFeignClient.testFeign();
    }

}
