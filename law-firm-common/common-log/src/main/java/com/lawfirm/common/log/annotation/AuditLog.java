package com.lawfirm.common.log.annotation;

import java.lang.annotation.*;

/**
 * 审计日志注解
 * 用于标记需要进行审计记录的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    
    /**
     * 模块名称
     */
    String module() default "";
    
    /**
     * 操作类型
     */
    String operateType() default "";

    /**
     * 业务类型
     */
    String businessType() default "";
    
    /**
     * 详细描述
     */
    String description() default "";
    
    /**
     * 是否异步执行
     */
    boolean async() default true;
    
    /**
     * 需要排除的字段
     */
    String[] excludeFields() default {};

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
} 