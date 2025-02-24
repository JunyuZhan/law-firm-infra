package com.lawfirm.common.security.annotation;

import java.lang.annotation.*;

/**
 * 权限校验注解
 * 用于标注需要进行权限检查的方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {
    
    /**
     * 需要校验的权限标识
     * @return 权限标识数组
     */
    String[] value();
    
    /**
     * 多个权限的逻辑关系
     * @return 权限的逻辑关系，默认为AND
     */
    Logical logical() default Logical.AND;
} 