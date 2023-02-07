package com.practice.bom.factory_bean.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * 实现FactoryBean接口，作为算法接口的工厂bean，对算法接口进行代理
 * getObject（）：从工厂中获取bean
 * getObjectType（）：获取Bean工厂创建的对象的类型，该方法返回的类型是在ioc容器中getBean所匹配的类型。
 * isSingleton（）：Bean工厂创建的对象是否是单例模式。
 *
 * @author ljf
 * @description 算法接口工厂bean
 * @date 2023/2/7 2:47 PM
 */
public class AlgorithmFactoryBean implements FactoryBean<AlgorithmService> {

    /**
     * 算法类型
     */
    private String algorithmType;

    @Override
    public AlgorithmService getObject() throws Exception {
        if (StringUtils.equals("MD5", this.algorithmType)) {
            return new MD5AlgorithmServiceImpl();
        }
        if (StringUtils.equals("AES", this.algorithmType)) {
            return new AESAlgorithmServiceImpl();
        }
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return AlgorithmService.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

}
