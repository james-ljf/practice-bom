package com.practice.bom.enums;

/**
 * @Author ljf
 * @Date 2023/6/13
 **/
public class Test {

    public static void main(String [] args){
        System.out.println(EnumsCache.findByName(StatusEnum.class, "SUCCESS", null));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumsCache.findByName(StatusEnum.class, null, StatusEnum.INIT));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumsCache.findByName(StatusEnum.class, "ERROR", StatusEnum.INIT));


        System.out.println(EnumsCache.findByValue(StatusEnum.class, "S", null));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumsCache.findByValue(StatusEnum.class, "T", StatusEnum.PROCESSING));
        // 返回默认值StatusEnum.INIT
        System.out.println(EnumsCache.findByValue(StatusEnum.class, "ERROR", StatusEnum.INIT));
    }

}
