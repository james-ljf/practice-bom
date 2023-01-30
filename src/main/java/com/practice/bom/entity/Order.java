package com.practice.bom.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ljf
 * @description 订单类
 * @date 2023/1/29 11:50 AM
 */
@Data
public class Order {

    /**
     * 订单id
     */
    private String id;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
