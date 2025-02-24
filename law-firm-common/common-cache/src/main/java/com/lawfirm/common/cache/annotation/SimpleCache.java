package com.lawfirm.common.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 简单缓存注解
 * 相比Spring的@Cacheable更轻量，更适合基础场景
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleCache {

    /**
     * 缓存key
     */
    String key() default "";

    /**
     * 超时时间
     */
    long timeout() default 60;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 是否使用方法名作为key的一部分
     */
    boolean useMethodName() default true;

    /**
     * 是否使用参数作为key的一部分
     */
    boolean useParams() default true;

    /**
     * 是否忽略异常
     */
    boolean ignoreException() default false;
} 