package com.lawfirm.common.data.enums;

import com.lawfirm.common.data.utils.DesensitizeUtils;
import lombok.Getter;

import java.util.function.Function;

/**
 * 脱敏类型枚举
 */
@Getter
public enum SensitiveType {
    
    /**
     * 手机号脱敏
     */
    PHONE(DesensitizeUtils::maskPhone),
    
    /**
     * 邮箱脱敏
     */
    EMAIL(DesensitizeUtils::maskEmail),
    
    /**
     * 身份证号脱敏
     */
    ID_CARD(DesensitizeUtils::maskIdCard),
    
    /**
     * 银行卡号脱敏
     */
    BANK_CARD(DesensitizeUtils::maskBankCard),
    
    /**
     * 地址脱敏
     */
    ADDRESS(DesensitizeUtils::maskAddress),
    
    /**
     * 姓名脱敏
     */
    NAME(DesensitizeUtils::maskName);
    
    /**
     * 脱敏函数
     */
    private final Function<String, String> desensitizer;
    
    SensitiveType(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    public String desensitize(String value) {
        return desensitizer.apply(value);
    }
} 