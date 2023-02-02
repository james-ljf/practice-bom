package com.practice.bom.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ljf
 * @description
 * @date 2023/2/2 3:26 PM
 */
@Data
public class UserNoticeDTO implements Serializable {

    private static final long serialVersionUID = -5232887953791531433L;

    private String noticeId;

    private String userId;

    private String name;

    private String description;

    private Integer hasRead;

}
