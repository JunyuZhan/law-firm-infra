package com.lawfirm.common.log.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 日志工具类
 */
public class LogUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取方法调用的完整信息
     */
    public static String getMethodCallInfo(String className, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(className).append("#").append(methodName);
        if (args != null && args.length > 0) {
            sb.append("(");
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(formatArg(args[i]));
            }
            sb.append(")");
        }
        return sb.toString();
    }

    /**
     * 格式化异常堆栈
     */
    public static String formatException(Throwable e) {
        if (e == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\n\tat ").append(element);
        }
        return sb.toString();
    }

    /**
     * 格式化参数值
     */
    public static String formatArg(Object arg) {
        if (arg == null) {
            return "null";
        }
        if (arg instanceof String) {
            return "\"" + arg + "\"";
        }
        if (arg instanceof Collection) {
            return "[" + ((Collection<?>) arg).size() + " items]";
        }
        if (arg instanceof Map) {
            return "{" + ((Map<?, ?>) arg).size() + " entries}";
        }
        if (arg.getClass().isArray()) {
            return Arrays.toString((Object[]) arg);
        }
        try {
            return objectMapper.writeValueAsString(arg);
        } catch (JsonProcessingException e) {
            return arg.toString();
        }
    }

    /**
     * 脱敏处理
     */
    public static String desensitize(String content, String[] sensitiveFields) {
        if (!StringUtils.hasText(content) || sensitiveFields == null || sensitiveFields.length == 0) {
            return content;
        }
        String result = content;
        for (String field : sensitiveFields) {
            result = result.replaceAll("\"" + field + "\"\\s*:\\s*\"[^\"]*\"",
                    "\"" + field + "\":\"***\"");
        }
        return result;
    }

    /**
     * 条件日志
     */
    public static void logIf(Logger logger, boolean condition, Supplier<String> msgSupplier) {
        if (condition && logger.isDebugEnabled()) {
            logger.debug(msgSupplier.get());
        }
    }

    /**
     * 带性能记录的日志
     */
    public static void logWithPerformance(Logger logger, String operation, Runnable task) {
        long startTime = System.currentTimeMillis();
        try {
            task.run();
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("{} took {} ms", operation, (endTime - startTime));
        }
    }
} 