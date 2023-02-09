package com.practice.bom.feign_client.config;

import com.practice.bom.feign_client.annotation.FeignClientMethod;
import com.practice.bom.feign_client.annotation.MyFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ljf
 * @description
 * @date 2023/2/9 4:38 PM
 */
@Slf4j
public class FeignFactoryBean<T> implements FactoryBean<T>, InvocationHandler {

    private final Map<String, Object> hostMap;

    /**
     * 存储目标类型
     */
    private final Class<T> targetClass;

    public FeignFactoryBean(Class<T> targetClass) {
        this.hostMap = new HashMap<>();
        this.targetClass = targetClass;
        hostMap.put("practice-user-service", "http://127.0.0.1:8090");
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), new Class[]{targetClass}, this);
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = null;
        // 获取方法上的注解信息
        MyFeignClient myFeignClient = targetClass.getAnnotation(MyFeignClient.class);
        FeignClientMethod feignClientMethod = method.getAnnotation(FeignClientMethod.class);
        if (myFeignClient == null || feignClientMethod == null) {
            return new Object();
        }
        String path = feignClientMethod.path();
        log.info("[FeignFactoryBean.invoke]：注解上的信息为 name = {}, path = {}", myFeignClient.name(), feignClientMethod.path());
        String urlPath = hostMap.get(myFeignClient.name()) + path;
        log.info("[FeignFactoryBean.invoke]：转换后的请求路径为{}", urlPath);
        // 发起请求
        result = post(urlPath);
        log.info("[FeignFactoryBean.invoke]：请求结果 = {}", result);
        return result;
    }

    /**
     * 使用 HttpURLConnection 进行post请求
     *
     * @param urlPath 请求路径
     * @return 请求结果
     */
    private String post(String urlPath) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //定义 BufferedReader 输入流来读取URL的响应
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
