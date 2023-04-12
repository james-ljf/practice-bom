package com.practice.bom.function;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * 数组流
 *
 * @Author ljf
 * @Date 2023/4/12
 **/
public class ArrayFlow<T> extends ArrayList<T> implements GFlow<T> {


    @Override
    public void consume(Consumer<T> consumer) {
        forEach(consumer);
    }

}
