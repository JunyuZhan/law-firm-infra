package com.lawfirm.core.audit.annotation;

import java.lang.annotation.*;

/**
 * 审计忽略注解
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditIgnore {
    /**
     * 忽略原因
     */
    String value() default "";
} 