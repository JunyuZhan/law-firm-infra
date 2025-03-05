package com.lawfirm.model.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型枚举
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 用户名密码登录
     */
    USERNAME_PASSWORD(1, "用户名密码登录"),

    /**
     * 短信验证码登录
     */
    SMS_CODE(2, "短信验证码登录"),

    /**
     * 邮箱验证码登录
     */
    EMAIL_CODE(3, "邮箱验证码登录"),

    /**
     * 微信登录
     */
    WECHAT(4, "微信登录"),
    
    /**
     * 钉钉登录
     */
    DINGTALK(5, "钉钉登录"),
    
    /**
     * LDAP登录
     */
    LDAP(6, "LDAP登录"),
    
    /**
     * 扫码登录
     */
    QR_CODE(7, "扫码登录");

    /**
     * 类型编码
     */
    @EnumValue
    private final Integer code;

    /**
     * 类型描述
     */
    private final String desc;
} 