package com.practice.bom.lazy_test;

import com.practice.bom.function.Lazy;
import lombok.Data;

import java.util.Set;

/**
 * @author ljf
 * @description
 * @date 2023/3/3 11:39 AM
 */
@Data
public class User {

    private String id;

    private String name;

    private String address;

    private Lazy<String> school;

    private Lazy<Set<String>> auths;


}
