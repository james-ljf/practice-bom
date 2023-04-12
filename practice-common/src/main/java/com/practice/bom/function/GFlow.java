package com.practice.bom.function;

import com.practice.bom.exception.StopException;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 生成器流，看似本质是consumer of consumer，实则是consumer of callback。
 * 流本身是lazy或者说尚未真正执行的，真正执行这个流需要使用所谓终端操作，类似Stream的Collector。
 *
 * @Author ljf
 * @Date 2023/4/12
 **/
@FunctionalInterface
public interface GFlow<T> {

    /**
     * 消费流
     *
     * @param consumer 功能方法
     */
    void consume(Consumer<T> consumer);

    /**
     * 并发流
     *
     * @return GFlow<T>
     */
    default GFlow<T> parallel() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return c -> map(t -> pool.submit(() -> c.accept(t))).cache().consume(ForkJoinTask::join);
    }

    /**
     * 异步消费
     *
     * @param consumer 功能方法
     */
    default void asyncConsume(Consumer<T> consumer) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        map(t -> pool.submit(() -> consumer.accept(t))).cache().consume(ForkJoinTask::join);
    }

    @SafeVarargs
    static <T> GFlow<T> of(T... ts) {
        return Arrays.asList(ts)::forEach;
    }

    /**
     * 对字符串进行连接的终端操作
     *
     * @param param 字符串
     * @return String
     */
    default String join(String param) {
        StringJoiner joiner = new StringJoiner(param);
        consume(t -> joiner.add(t.toString()));
        return joiner.toString();
    }

    /**
     * 转换为List的终端操作
     *
     * @return List<T>
     */
    default List<T> toList() {
        List<T> list = new ArrayList<>();
        consume(list::add);
        return list;
    }

    /**
     * 流转换
     *
     * @param function 函数
     * @param <E>      转换结果类型
     * @return GFlow<E>
     */
    default <E> GFlow<E> map(Function<T, E> function) {
        return c -> consume(t -> c.accept(function.apply(t)));
    }

    /**
     * 展开元素合并后再转换
     *
     * @param function 函数
     * @param <E>      转换结果类型
     * @return GFlow<E>
     */
    default <E> GFlow<E> flatMap(Function<T, GFlow<E>> function) {
        return c -> consume(t -> function.apply(t).consume(c));
    }

    /**
     * 元素过滤
     *
     * @param predicate 功能接口
     * @return GFlow<E>
     */
    default GFlow<T> filter(Predicate<T> predicate) {
        return c -> consume(t -> {
            if (predicate.test(t)) {
                c.accept(t);
            }
        });
    }

    /**
     * 流中断，即获取前n个元素，后面的不要——等价于Stream.limit
     * 由于GFlow并不依赖Iterator，所以必须通过异常实现中断
     *
     * @param n 前n个
     * @return GFlow<E>
     */
    default GFlow<T> take(int n) {
        return c -> {
            int[] i = {n};
            consumeTillStop(t -> {
                if (i[0]-- > 0) {
                    c.accept(t);
                } else {
                    stop();
                }
            });
        };
    }

    /**
     * 丢弃前n个元素——等价于Stream.skip。它并不涉及流的中断控制，更像是filter的变种，一种带有状态的filter。
     * 内部随着流的迭代而刷新计数器的状态
     *
     * @param n 前n个元素
     * @return GFlow<T>
     */
    default GFlow<T> drop(int n) {
        return c -> {
            int[] a = {n - 1};
            consumeTillStop(t -> {
                if (a[0] < 0) {
                    c.accept(t);
                } else {
                    a[0]--;
                }
            });
        };
    }

    /**
     * 对流的某个元素添加一个操作consumer，但是不执行流。同理如Stream.peek()
     *
     * @param consumer 功能方法
     * @return GFlow<T>
     */
    default GFlow<T> peek(Consumer<T> consumer) {
        return c -> consume(consumer.andThen(c));
    }

    /**
     * 流与一个Iterable元素两两聚合，然后转换为一个新的流。
     *
     * @param iterable 迭代器
     * @param function 双参数的流函数
     * @param <E>      参数1
     * @param <R>      参数2
     * @return GFlow<R>
     */
    default <E, R> GFlow<R> zip(Iterable<E> iterable, BiFunction<T, E, R> function) {
        return c -> {
            Iterator<E> iterator = iterable.iterator();
            consumeTillStop(t -> {
                if (iterator.hasNext()) {
                    c.accept(function.apply(t, iterator.next()));
                } else {
                    stop();
                }
            });
        };
    }

    /**
     * 流的缓存
     *
     * @return GFlow<T>
     */
    default GFlow<T> cache() {
        ArrayFlow<T> arrayFlow = new ArrayFlow<>();
        consume(arrayFlow::add);
        return arrayFlow;
    }

    static <T> T stop() {
        throw StopException.INSTANCE;
    }

    default void consumeTillStop(Consumer<T> consumer) {
        try {
            consume(consumer);
        } catch (StopException ignore) {
        }
    }

}
