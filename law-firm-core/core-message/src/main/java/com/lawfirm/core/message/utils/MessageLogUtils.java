package com.lawfirm.core.message.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lawfirm.common.log.util.LogUtils;

public class MessageLogUtils {
    private static final Logger log = LoggerFactory.getLogger(MessageLogUtils.class);
    private static final String[] SENSITIVE_FIELDS = {"password", "token", "secret"};

    public static void logMessageSend(String messageId, Object message) {
        final String content = LogUtils.formatArg(message);
        final String desensitizedContent = LogUtils.desensitize(content, SENSITIVE_FIELDS);
        LogUtils.logWithPerformance(log, "Message sent - ID: " + messageId, () -> {
            log.info("Message Content: {}", desensitizedContent);
        });
    }

    public static void logMessageReceive(String messageId, Object message) {
        final String content = LogUtils.formatArg(message);
        final String desensitizedContent = LogUtils.desensitize(content, SENSITIVE_FIELDS);
        LogUtils.logWithPerformance(log, "Message received - ID: " + messageId, () -> {
            log.info("Message Content: {}", desensitizedContent);
        });
    }

    public static void logMessageError(String messageId, String error, Throwable throwable) {
        log.error("Message error - ID: {}, Error: {}\nStack trace: {}", 
            messageId, error, LogUtils.formatException(throwable));
    }

    public static void logMessageProcess(String messageId, String status) {
        LogUtils.logWithPerformance(log, "Message process - ID: " + messageId, () -> {
            log.info("Status: {}", status);
        });
    }
} 