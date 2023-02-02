package com.practice.bom.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ljf
 * @description 通知信息
 * @date 2023/2/2 3:22 PM
 */
@Data
public class NoticeBO implements Serializable {

    private static final long serialVersionUID = 2045022736257041789L;

    private String noticeId;

    private String name;

    private String description;

    private int hasRead;

}
