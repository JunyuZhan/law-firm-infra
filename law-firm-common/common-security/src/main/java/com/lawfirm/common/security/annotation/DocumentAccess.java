package com.lawfirm.common.security.annotation;

import java.lang.annotation.*;

/**
 * 文档访问控制注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DocumentAccess {
    /**
     * 允许访问的文档类型
     */
    String[] types() default {};

    /**
     * 允许的操作类型（如：read, write, delete）
     */
    String[] operations() default {};

    /**
     * 验证模式：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;

    /**
     * 是否检查文档所有者，默认检查
     */
    boolean checkOwner() default true;
} 