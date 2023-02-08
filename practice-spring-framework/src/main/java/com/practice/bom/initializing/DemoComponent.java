package com.practice.bom.initializing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * 实现InitializingBean和SmartInitializingSingleton接口，并重写其方法，可在初始化bean后做一些回调操作
 * InitializingBean 是在每一个bean 初始化完成后调用；多例的情况下每初始化一次就掉用一次。
 * SmartInitializingSingleton是所有的非延迟的、单例的bean 都初始化后调用，只调用一次。如果是多例的bean实现，不会调用。
 *
 * @author ljf
 * @description
 * @date 2023/2/8 10:18 AM
 */
@Slf4j
@Component
public class DemoComponent implements InitializingBean, SmartInitializingSingleton {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("我是InitializingBean.afterPropertiesSet()");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("我是SmartInitializingSingleton.afterSingletonsInstantiated()");
    }

}
