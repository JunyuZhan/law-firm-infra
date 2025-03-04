package com.lawfirm.common.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 用于标记需要记录操作日志的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    
    /**
     * 日志标题
     */
    String title() default "";
    
    /**
     * 业务类型
     */
    String businessType() default "";
    
    /**
     * 是否保存请求参数
     */
    boolean isSaveRequestData() default true;
    
    /**
     * 是否保存响应数据
     */
    boolean isSaveResponseData() default true;
    
    /**
     * 排除指定的请求参数
     */
    String[] excludeParamNames() default {};
} 