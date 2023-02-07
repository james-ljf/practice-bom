package com.practice.bom.factory_bean.service;

import org.springframework.stereotype.Service;

/**
 * @author ljf
 * @description
 * @date 2023/2/7 2:46 PM
 */

public class AESAlgorithmServiceImpl implements AlgorithmService {

    @Override
    public String encrypt(String text) {
        return "AES算法实现";
    }

}
