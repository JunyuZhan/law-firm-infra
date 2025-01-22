package com.lawfirm.common.data.annotation;

import java.lang.annotation.*;

/**
 * 数据源切换注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    /**
     * 数据源名称
     */
    String value() default "";
} 