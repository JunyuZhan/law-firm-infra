package com.lawfirm.api.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交注解
 * 用于标记需要防止重复提交的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmitPrevention {

    /**
     * 防重复提交间隔（毫秒）
     * 在此时间段内的重复请求将被拒绝
     */
    int interval() default 5000;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 提示消息
     */
    String message() default "请勿重复提交";
    
    /**
     * 是否基于用户隔离
     * 若为true，则同一用户不能重复提交
     * 若为false，则同一会话不能重复提交
     */
    boolean userBased() default true;
} 