package com.practice.bom.lazy_test;

import java.util.function.BiPredicate;

/**
 * 测试---判断条件封装，简化if elseif
 * @author ljf
 * @description
 * @date 2023/3/3 4:22 PM
 */
public enum TestEnum {

    /**
     * 用户判断
     */
    USER((roleId, selectParam) -> (roleId == 1 || roleId == 2) && selectParam == 2, 1),

    TEACHER((roleId, selectParam) -> roleId == 1 && selectParam == 7, 3);

    private final BiPredicate<Integer, Integer> biPredicate;

    private final int status;

    TestEnum(BiPredicate<Integer, Integer> biPredicate, int status) {
        this.biPredicate = biPredicate;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public BiPredicate<Integer, Integer> getBiPredicate() {
        return biPredicate;
    }

    public static void main(String[] args) {
        int roleId = 1;
        int selectParam = 2;
        Integer newData = null;
        if (TestEnum.USER.getBiPredicate().test(roleId, selectParam)) {
            newData = TestEnum.USER.getStatus();
        }
        System.out.println(newData);
    }

}
