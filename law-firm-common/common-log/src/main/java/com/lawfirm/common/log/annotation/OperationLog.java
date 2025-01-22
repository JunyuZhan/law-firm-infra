package com.lawfirm.common.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    
    /**
     * 操作描述
     */
    String description() default "";
    
    /**
     * 操作类型
     */
    String operationType() default "";
    
    /**
     * 是否保存请求参数
     */
    boolean saveRequestData() default true;
    
    /**
     * 是否保存响应数据
     */
    boolean saveResponseData() default true;
} 