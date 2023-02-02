package com.practice.bom.listener.handler.rocketmq;

import com.practice.bom.constants.GlobalStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ljf
 * @description rocketmq事务消息监听器
 * @date 2023/2/1 10:26 AM
 */
@Slf4j
@Component
@RocketMQTransactionListener
public class RqTransactionListenerHandler implements RocketMQLocalTransactionListener {

    private final ConcurrentHashMap<String, String> localTransactionMap = new ConcurrentHashMap<>();

    /**
     * 发送消息成功此方法被回调，该方法用于执行本地事务，利用transactionId即可获取到该消息的唯一Id
     *
     * @param message 回传的消息，
     * @param args    事务监听器回查的参数，在send的时候可以传入
     * @return 返回事务状态，COMMIT：提交  ROLLBACK：回滚  UNKNOW：回调
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object args) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        if (transactionId == null) {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        String transactionArgs = String.valueOf(args);
        try {
            log.info("this local transaction msg : id = {}, args = {}", transactionId, transactionArgs);
            localTransactionMap.put(transactionId, GlobalStatus.SUCCESS.getStatus());
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("this local transaction error : {}", e.getMessage());
            localTransactionMap.put(transactionId, GlobalStatus.FAIL.getStatus());
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    /**
     * 此方法可回调检查本地事务执行情况
     *
     * @param message 消息体
     * @return 消息本地事务状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("this local transaction msg : id = {}", transactionId);
        if (StringUtils.equals(localTransactionMap.get(transactionId), GlobalStatus.SUCCESS.getStatus())) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
