package com.practice.bom.bean_facory_post_processor;

import com.practice.bom.util.AesUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 通过实现EnvironmentAware获取当前运行环境的对象，
 * BeanFactoryPostProcessor在容器实例化任何其它bean之前读取配置元数据，实现BeanFactoryPostProcessor对未实例化的bean进行定制或修改
 * @author ljf
 * @description 解密配置文件属性值配置
 * @date 2023/2/7 11:44 AM
 */
@Component
public class PropertyDecryptProcessor implements EnvironmentAware, BeanFactoryPostProcessor, Ordered {

    private ConfigurableEnvironment environment;

    /**
     * 解密前缀标志 默认值
     */
    private String decryptPrefix = "Abandon[";

    /**
     * 解密后缀标志 默认值
     */
    private String decryptSuffix = "]";

    /**
     * 解密秘钥 默认值
     */
    private String decryptKey = "xj9Y9rvwbwcgDIoN";

    @Override
    public void setEnvironment(@Nonnull Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void postProcessBeanFactory(@Nonnull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取当前程序运行环境对象
        MutablePropertySources propSources = environment.getPropertySources();
        StreamSupport.stream(propSources.spliterator(), false)
                // 过滤出对应类的实例
                .filter(OriginTrackedMapPropertySource.class::isInstance)
                .collect(Collectors.toList())
                .forEach(ps -> convertPropertySource(propSources, (PropertySource<Map<String, Object>>) ps));
    }

    /**
     * 解密相关属性
     *
     * @param ps : 源对象属性信息
     */
    private void convertPropertySource(MutablePropertySources propSources, PropertySource<Map<String, Object>> ps) {
        // 不可变的map，存储基础PropertySource源对象
        Map<String, Object> source = ps.getSource();
        setDecryptProperties(source);
        // 因为source是不可变的，因此需要重新new一个map做put操作，否则会抛出异常UnsupportedOperationException
        Map<Object, Object> newMap = new HashMap<>(source.size());
        source.forEach((k, v) -> {
            String value = String.valueOf(v);
            // 如果配置值是指定前缀后缀包围的则需进行解密
            if (value.startsWith(decryptPrefix) && value.endsWith(decryptSuffix)) {
                String cipherText = value.replace(decryptPrefix, "").replace(decryptSuffix, "");
                value = AesUtil.aesDecrypt(cipherText, decryptKey);
            }
            newMap.put(k, value);
        });
        propSources.replace(ps.getName(), new OriginTrackedMapPropertySource(ps.getName(), newMap));
    }


    /**
     * 设置解密属性
     *
     * @param source 配置上下文信息map
     */
    private void setDecryptProperties(Map<String, Object> source) {
        decryptPrefix = source.get("decrypt.prefix") == null ? decryptPrefix : String.valueOf(source.get("decrypt.prefix"));
        decryptSuffix = source.get("decrypt.suffix") == null ? decryptSuffix : String.valueOf(source.get("decrypt.suffix"));
        decryptKey = source.get("decrypt.key") == null ? decryptKey : String.valueOf(source.get("decrypt.key"));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
