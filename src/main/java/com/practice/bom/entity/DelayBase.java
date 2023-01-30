package com.practice.bom.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ljf
 * @description 延迟队列数据类
 * @date 2023/1/29 2:13 PM
 */
@Getter
@Setter
@ToString
public class DelayBase<T> implements Delayed {

    /**
     * 唯一id
     */
    private String id;

    /**
     * 数据泛型实体
     */
    private T data;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 过期时间，默认60秒 单位ms
     */
    private Long expireVal = 1000 * 30L;


    @Override
    public long getDelay(TimeUnit unit) {
        long expireTime = this.createdTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() + this.expireVal;
        return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
