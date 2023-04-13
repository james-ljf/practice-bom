package com.practice.bom.function;

import com.practice.bom.ApplicationTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @Author ljf
 * @Date 2023/4/12
 **/
public class FunctionTest extends ApplicationTest {


    @Test
    public void testLowerCase() {
        String str = "hi_tom_san_go";
        UnaryOperator<String> capitalize = s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        GFlow<UnaryOperator<String>> flow = c -> {
            c.accept(String::toLowerCase);
            while (true) {
                c.accept(capitalize);
            }
        };
        List<String> split = Arrays.asList(str.split("_"));
        String resText = flow.zip(split, Function::apply).join("");
        System.out.println(resText);
    }

    @Test
    public void testChan() {
        // 生产无限的自然数，放入通道seq
        GFlow<Long> seq = c -> {
            long i = 0;
            while (true) {
                c.accept(i++);
            }
        };
        long start = System.currentTimeMillis();
        // 通道seq交给消费者，只要偶数且只要5个
        seq.filter(i -> (i & 1) == 0).take(5).asyncConsume(i -> {
            try {
                Thread.sleep(1000);
                System.out.println("消费 " +  i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.printf("耗时: %dms\n", System.currentTimeMillis() - start);
    }

    @Test
    public void testStream() {
        GFlow<Integer> flow = c -> {
            c.accept(0);
            for (int i = 1; i < 5; i++) {
                c.accept(i);
            }
        };
        Stream<Integer> stream = GFlow.stream(flow);
        System.out.println(stream.collect(Collectors.toList()));
    }

}
