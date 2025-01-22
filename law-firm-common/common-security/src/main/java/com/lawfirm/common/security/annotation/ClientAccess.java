package com.lawfirm.common.security.annotation;

import java.lang.annotation.*;

/**
 * 客户端访问控制注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClientAccess {
    /**
     * 允许访问的客户端类型
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;

    /**
     * 是否需要认证，默认需要
     */
    boolean authenticated() default true;
} 