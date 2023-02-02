package com.practice.bom.manger;

import com.practice.bom.entity.DelayBase;
import com.practice.bom.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.DelayQueue;

/**
 * @author ljf
 * @description DelayQueue延迟队列管理器
 * @date 2023/1/29 12:05 PM
 */
@Slf4j
@Component
public class OrderDelayQueueManger implements CommandLineRunner {

    private final DelayQueue<DelayBase<Order>> delayQueue = new DelayQueue<>();

    @Resource
    @Qualifier("delayExecutorService")
    private ThreadPoolTaskExecutor delayExecutorService;

    public void offer(DelayBase<Order> delayBase) {
        boolean isOffer = delayQueue.offer(delayBase);
        log.info("加入延迟队列：code = {}, data = {}", isOffer, delayBase);
    }

    public boolean remove(String id) {
        DelayBase<Order> delayBase = new DelayBase<>();
        delayBase.setId(id);
        delayBase.setExpireVal(0L);
        return delayQueue.remove(delayBase);
    }

    public void execute() {
        DelayBase<Order> delayBase;
        while (!delayQueue.isEmpty()) {
            try {
                delayBase = delayQueue.take();
                log.info("订单过期，信息 = {}", delayBase);
                remove(delayBase.getId());
                log.info("剩余未过期订单数量：{} ", delayQueue.size());
            } catch (InterruptedException e) {
                log.error("take order has error ：{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        delayExecutorService.execute(this::execute);
    }
}
