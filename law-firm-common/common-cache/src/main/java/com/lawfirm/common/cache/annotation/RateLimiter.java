package com.lawfirm.common.cache.annotation;

import org.redisson.api.RateIntervalUnit;

import java.lang.annotation.*;

/**
 * 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     */
    String key() default "";

    /**
     * 速率
     */
    long rate() default 1;

    /**
     * 速率间隔
     */
    long rateInterval() default 1;

    /**
     * 速率间隔单位
     */
    RateIntervalUnit rateIntervalUnit() default RateIntervalUnit.SECONDS;

    /**
     * 限流提示语
     */
    String message() default "请求过于频繁，请稍后重试";
} 