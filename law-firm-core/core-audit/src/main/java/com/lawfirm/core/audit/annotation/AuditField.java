package com.lawfirm.core.audit.annotation;

import java.lang.annotation.*;

/**
 * 字段级审计注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditField {
    /**
     * 需要审计的字段名数组
     */
    String[] fields();

    /**
     * 是否异步处理
     */
    boolean async() default true;

    /**
     * 字段变更描述
     */
    String description() default "";
} 