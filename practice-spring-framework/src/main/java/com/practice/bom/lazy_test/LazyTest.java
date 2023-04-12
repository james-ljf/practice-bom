package com.practice.bom.lazy_test;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.practice.bom.function.Lazy;

import java.util.Set;

/**
 * @author ljf
 * @description
 * @date 2023/3/3 11:40 AM
 */
public class LazyTest {

    public static void main(String[] args) {
        boolean isLazy = true;
        User user = new User();
        user.setId(IdUtil.fastSimpleUUID());
        user.setName("测试人员");
        if (isLazy) {
            user.setSchool(Lazy.of(LazyTest::getUserSchool));
        }
        System.out.println(user);

        SysUser sysUser = JSON.parseObject(JSON.toJSONString(user), SysUser.class);
//        sysUser.setSchool(user.getSchool().get());
        System.out.println(sysUser);
    }


    public static String getUserSchool() {
        return "厦门大学";
    }

    public static String getUserSchoolV2() {
        return "清华大学";
    }



    public static Set<String> getUserAuths() {
        return  Sets.newHashSet("10000", "10001");
    }

}
