package com.lawfirm.common.log.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * 日志工具类
 */
@Component("commonLogUtils")
public class LogUtils {
    private static volatile ObjectMapper staticObjectMapper;
    
    // 读写锁，确保线程安全
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = LOCK.writeLock();
    
    @Autowired
    @Qualifier("commonCoreObjectMapper")
    private ObjectMapper objectMapper;
    
    @PostConstruct
    public void init() {
        WRITE_LOCK.lock();
        try {
            if (staticObjectMapper == null) {
                staticObjectMapper = objectMapper;
            }
        } finally {
            WRITE_LOCK.unlock();
        }
    }
    
    /**
     * 获取ObjectMapper实例，确保线程安全
     */
    private static ObjectMapper getObjectMapper() {
        ObjectMapper localMapper = staticObjectMapper;
        if (localMapper != null) {
            return localMapper;
        }
        
        // 如果还未初始化，创建默认实例
        WRITE_LOCK.lock();
        try {
            if (staticObjectMapper == null) {
                staticObjectMapper = new ObjectMapper();
                staticObjectMapper.findAndRegisterModules();
            }
            return staticObjectMapper;
        } finally {
            WRITE_LOCK.unlock();
        }
    }

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
            return getObjectMapper().writeValueAsString(arg);
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