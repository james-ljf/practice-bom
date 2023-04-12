package com.practice.bom.exception;

/**
 * 全局流中断异常类
 *
 * @Author ljf
 * @Date 2023/4/12
 **/
public final class StopException extends RuntimeException {

    public static final StopException INSTANCE = new StopException();

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
