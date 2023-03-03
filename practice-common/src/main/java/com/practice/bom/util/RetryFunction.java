package com.practice.bom.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author ljf
 * @description 重试工具类
 * @date 2023/3/3 2:49 PM
 */
@Slf4j
public class RetryFunction {

    public static void retryFunction(Runnable runnable, int time) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (Exception e) {
                time--;
                if (time <= 0) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Object retryFunction(Callable callable, int time) {
        while (true) {
            try {
                return callable.call();
            } catch (Exception e) {
                time--;
                if (time <= 0) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static <T, R> R retryFunction(Function<T, R> function, T param, int time) {
        while (true) {
            try {
                return function.apply(param);
            } catch (Exception e) {
                time--;
                if (time <= 0) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static <T, U, R> R retryFunction(BiFunction<T, U, R> function, T param1, U param2, int time) {
        while (true) {
            try {
                return function.apply(param1, param2);
            } catch (Exception e) {
                time--;
                if (time <= 0) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
