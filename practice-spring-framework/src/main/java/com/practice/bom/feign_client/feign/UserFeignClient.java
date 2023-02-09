package com.practice.bom.feign_client.feign;

import com.practice.bom.feign_client.annotation.FeignClientMethod;
import com.practice.bom.feign_client.annotation.MyFeignClient;

/**
 * @author ljf
 * @description
 * @date 2023/2/9 4:58 PM
 */
@MyFeignClient(name = "practice-user-service")
public interface UserFeignClient {

    /**
     * 测试自定义feign
     *
     * @return Object
     */
    @FeignClientMethod(path = "/test-feign")
    Object testFeign();

}
