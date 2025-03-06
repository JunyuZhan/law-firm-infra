package com.lawfirm.model.auth.annotation;

import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;

import java.lang.annotation.*;

/**
 * 权限检查注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {

    /**
     * 模块类型
     */
    ModuleTypeEnum module();

    /**
     * 操作类型
     */
    OperationTypeEnum operation();

    /**
     * 是否需要数据权限检查
     */
    boolean requireDataScope() default true;

    /**
     * 错误提示信息
     */
    String message() default "权限不足";
} 