package com.lawfirm.common.security.annotation;

/**
 * 权限验证模式
 */
public enum Logical {
    /**
     * 必须具有所有的权限
     */
    AND,

    /**
     * 只需具有其中一个权限
     */
    OR
} 