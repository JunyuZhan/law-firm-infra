package com.lawfirm.api.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 幂等性控制注解
 * 用于标记需要保证幂等性的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * 幂等性标识的来源
     * 默认从请求头中获取
     */
    IdempotentType type() default IdempotentType.HEADER;

    /**
     * 幂等性标识的名称
     * 如果type为HEADER，则表示请求头的名称
     * 如果type为PARAMETER，则表示请求参数的名称
     */
    String name() default "X-Idempotent-Token";

    /**
     * 过期时间
     * 幂等性标识的有效期
     */
    long expireTime() default 60;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 提示消息
     */
    String message() default "重复请求，请稍后再试";
    
    /**
     * 是否自动生成幂等性标识
     * 如果为true，则在请求中未找到幂等性标识时自动生成一个
     */
    boolean autoGenerate() default false;

    /**
     * 幂等性标识的来源类型
     */
    enum IdempotentType {
        /**
         * 从请求头中获取
         */
        HEADER,
        
        /**
         * 从请求参数中获取
         */
        PARAMETER,
        
        /**
         * 从请求体中获取
         */
        BODY
    }
} 