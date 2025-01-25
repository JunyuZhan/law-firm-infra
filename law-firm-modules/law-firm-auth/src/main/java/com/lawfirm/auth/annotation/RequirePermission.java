package com.lawfirm.auth.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    
    /**
     * 权限字符串
     */
    String value();
    
    /**
     * 是否需要所有权限
     */
    boolean requireAll() default false;
} 