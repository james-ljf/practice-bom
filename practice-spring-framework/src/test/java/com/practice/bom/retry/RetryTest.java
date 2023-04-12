package com.practice.bom.retry;

import com.practice.bom.ApplicationTest;
import com.practice.bom.function.RetryFunction;
import org.junit.Test;

/**
 * @author ljf
 * @description
 * @date 2023/3/3 3:33 PM
 */
public class RetryTest extends ApplicationTest {

    @Test
    public void testRetry() {
        RetryFunction.retryFunction(RetryTest::print, "什么", 3);
    }

    public static String print(String res) {
        System.out.println("进入重试");
        throw new IllegalArgumentException("重试中...");
    }

}
