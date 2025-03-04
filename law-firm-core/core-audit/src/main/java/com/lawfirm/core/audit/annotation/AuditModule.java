package com.lawfirm.core.audit.annotation;

import java.lang.annotation.*;

/**
 * 模块级审计注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditModule {
    /**
     * 模块名称
     */
    String value();

    /**
     * 是否启用审计
     */
    boolean enabled() default true;

    /**
     * 模块描述
     */
    String description() default "";
} 