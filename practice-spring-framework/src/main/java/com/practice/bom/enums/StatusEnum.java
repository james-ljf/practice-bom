package com.practice.bom.enums;

/**
 * @Author ljf
 * @Date 2023/6/13
 **/
public enum StatusEnum {

    INIT("I", "初始化"),
    PROCESSING("P", "处理中"),
    SUCCESS("S", "成功"),
    FAIL("F", "失败");

    private final String code;
    private final String desc;

    StatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    static {
        EnumsCache.registerByName(StatusEnum.class, StatusEnum.values());
        EnumsCache.registerByValue(StatusEnum.class, StatusEnum.values(), StatusEnum::getCode);
    }

}
