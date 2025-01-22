package com.lawfirm.common.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存预热注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheWarmUp {

    /**
     * 缓存键前缀
     */
    String keyPrefix() default "";

    /**
     * 是否使用方法名作为键的一部分
     */
    boolean useMethodName() default true;

    /**
     * 是否使用参数作为键的一部分
     */
    boolean useParams() default true;
} 