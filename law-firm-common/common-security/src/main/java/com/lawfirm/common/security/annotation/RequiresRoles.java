package com.lawfirm.common.security.annotation;

import java.lang.annotation.*;

/**
 * 角色校验注解
 * 用于标注需要进行角色检查的方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRoles {
    
    /**
     * 需要校验的角色标识
     * @return 角色标识数组
     */
    String[] value();
    
    /**
     * 多个角色的逻辑关系
     * @return 角色的逻辑关系，默认为AND
     */
    Logical logical() default Logical.AND;
} 