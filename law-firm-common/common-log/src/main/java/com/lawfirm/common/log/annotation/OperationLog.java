package com.lawfirm.common.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 用于标记需要记录操作日志的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    
    /**
     * 操作描述
     */
    String description() default "";
    
    /**
     * 操作类型
     */
    String operationType() default "";
    
    /**
     * 是否异步执行
     */
    boolean async() default true;
    
    /**
     * 是否记录请求参数
     */
    boolean logParams() default true;
    
    /**
     * 是否记录返回值
     */
    boolean logResult() default true;
    
    /**
     * 是否记录异常堆栈
     */
    boolean logStackTrace() default true;
    
    /**
     * 需要排除的字段
     */
    String[] excludeFields() default {};
} 