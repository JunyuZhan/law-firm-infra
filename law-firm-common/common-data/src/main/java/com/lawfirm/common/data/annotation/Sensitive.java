package com.lawfirm.common.data.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lawfirm.common.data.enums.SensitiveType;
import com.lawfirm.common.data.serializer.SensitiveSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerializer.class)
public @interface Sensitive {
    
    /**
     * 脱敏类型
     */
    SensitiveType type();

    /**
     * 自定义参数，用于特殊的脱敏规则
     */
    String[] params() default {};
} 