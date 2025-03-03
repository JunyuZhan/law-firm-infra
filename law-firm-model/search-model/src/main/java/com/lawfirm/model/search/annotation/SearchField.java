package com.lawfirm.model.search.annotation;

import java.lang.annotation.*;

/**
 * ES字段注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SearchField {

    /**
     * 字段类型
     */
    String type() default "text";

    /**
     * 分词器
     */
    String analyzer() default "";

    /**
     * 权重
     */
    float boost() default 1.0f;
} 