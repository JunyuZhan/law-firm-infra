package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 认证方式枚举
 */
@Getter
public enum AuthTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 用户名密码认证
     */
    USERNAME_PASSWORD(0, "用户名密码认证"),
    
    /**
     * 短信验证码认证
     */
    SMS_CODE(1, "短信验证码认证"),
    
    /**
     * 邮箱验证码认证
     */
    EMAIL_CODE(2, "邮箱验证码认证"),
    
    /**
     * LDAP认证
     */
    LDAP(3, "LDAP认证"),
    
    /**
     * CA认证
     */
    CA(4, "CA认证"),
    
    /**
     * 人脸识别认证
     */
    FACE(5, "人脸识别认证"),
    
    /**
     * 指纹认证
     */
    FINGERPRINT(6, "指纹认证"),
    
    /**
     * 微信认证
     */
    WECHAT(7, "微信认证"),
    
    /**
     * 钉钉认证
     */
    DINGTALK(8, "钉钉认证");

    private final Integer code;
    private final String desc;
    
    AuthTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
} 