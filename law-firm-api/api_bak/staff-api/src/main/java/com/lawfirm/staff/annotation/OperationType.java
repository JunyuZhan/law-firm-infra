package com.lawfirm.staff.annotation;

import java.lang.annotation.*;

/**
 * 操作类型注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationType {
    
    /**
     * 操作类型
     */
    String value() default "";
    
    /**
     * 操作描述
     */
    String description() default "";
} 