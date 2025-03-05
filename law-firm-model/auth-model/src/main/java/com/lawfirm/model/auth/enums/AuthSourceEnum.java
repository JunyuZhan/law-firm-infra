package com.lawfirm.model.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证来源枚举
 */
@Getter
@AllArgsConstructor
public enum AuthSourceEnum {

    /**
     * 网页端
     */
    WEB(1, "网页端"),

    /**
     * 移动端
     */
    MOBILE(2, "移动端"),

    /**
     * 小程序
     */
    MINI_PROGRAM(3, "小程序"),
    
    /**
     * 开放API
     */
    OPEN_API(4, "开放API"),
    
    /**
     * 系统内部
     */
    SYSTEM(5, "系统内部"),
    
    /**
     * 后台管理
     */
    ADMIN(6, "后台管理");

    /**
     * 来源编码
     */
    @EnumValue
    private final Integer code;

    /**
     * 来源描述
     */
    private final String desc;
} 