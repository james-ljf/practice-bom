package com.practice.bom.function;

import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author ljf
 * @description 懒加载工具
 * @date 2023/3/3 11:34 AM
 */
public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    private T data;

    public Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    /**
     * 对象中一个惰性加载的属性需要另一个惰性的属性才能获取到，可通过该方法进行获取。
     * 但此方法返回结果的嵌套层数取决于function的嵌套层数
     *
     * @param function 方法
     * @param <S>      泛型
     * @return Lazy<S>
     */
    public <S> Lazy<S> map(Function<? super T, ? extends S> function) {
        return Lazy.of(() -> function.apply(get()));
    }

    /**
     * 对象中一个惰性加载的属性需要另一个惰性的属性才能获取到，可通过该方法进行获取。
     * 需注意，此方法只有最内层使用map，其余层均使用flatMap进行嵌套获取。
     * 此方法返回结果的嵌套层数永远只有一层Lazy
     *
     * @param function 方法
     * @param <S>      泛型
     * @return Lazy<S>
     */
    public <S> Lazy<S> flatMap(Function<? super T, Lazy<? extends S>> function) {
        return Lazy.of(() -> function.apply(get()).get());
    }

    @Override
    public T get() {
        if (data == null) {
            synchronized (Lazy.class) {
                T newData = supplier.get();
                if (newData == null) {
                    throw new IllegalStateException("Lazy data can not be null!");
                }
                data = newData;
            }
        }
        return data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Lazy.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .toString();
    }
}
