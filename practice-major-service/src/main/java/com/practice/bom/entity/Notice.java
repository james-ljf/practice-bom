package com.practice.bom.entity;

import lombok.Data;

/**
 * @author ljf
 * @description
 * @date 2023/1/9 5:11 PM
 */
@Data
public class Notice {

    private String id;

    private String name;

    private String description;

    private int hasRead;

}
