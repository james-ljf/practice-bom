package com.practice.bom.constants;

import lombok.Getter;

/**
 * @author ljf
 * @description 全局状态
 * @date 2023/2/1 10:55 AM
 */
@Getter
public enum GlobalStatus {

    /**
     * 成功
     */
    SUCCESS("success"),

    /**
     * 失败
     */
    FAIL("fail");

    private final String status;


    GlobalStatus(String status) {
        this.status = status;
    }


}
