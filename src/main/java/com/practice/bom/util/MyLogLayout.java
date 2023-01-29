package com.practice.bom.util;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

import java.sql.Timestamp;

/**
 * @author ljf
 * @description
 * @date 2023/1/5 6:48 PM
 */
public class MyLogLayout extends LayoutBase<ILoggingEvent> {

    private static final String PROJECT_NAME = "practice-bom";

    @Override
    public String doLayout(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"P\":");
        sb.append("\"" + PROJECT_NAME + "\", ");
        sb.append("\"T\":");
        sb.append("\"").append(new Timestamp(event.getTimeStamp())).append("\"");
        sb.append(", \"L\":");
        sb.append("\"").append(event.getLevel()).append("\"");
        sb.append(", \"THREAD\":");
        sb.append("\"").append(event.getThreadName()).append("\"");
        sb.append(", \"CLASS\": ");
        sb.append("\"").append(event.getLoggerName()).append("\"");
        sb.append(",\"message\": ");
        String message = event.getFormattedMessage();
        if (event.getThrowableProxy() != null) {
            ExtendedThrowableProxyConverter throwableConverter = new ExtendedThrowableProxyConverter();
            throwableConverter.start();
            message = event.getFormattedMessage() + "\n" + throwableConverter.convert(event);
            throwableConverter.stop();
        }
        sb.append("\"").append(message).append("\"");
        sb.append("}");
        sb.append(CoreConstants.LINE_SEPARATOR);
        return sb.toString();
    }
}

