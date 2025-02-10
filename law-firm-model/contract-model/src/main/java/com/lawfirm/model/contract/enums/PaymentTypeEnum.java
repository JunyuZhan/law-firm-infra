package com.lawfirm.model.contract.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 付款类型枚举
 */
@Getter
public enum PaymentTypeEnum {
    
    LUMP_SUM("LUMP_SUM", "一次性付款"),
    INSTALLMENT("INSTALLMENT", "分期付款"),
    MILESTONE("MILESTONE", "里程碑付款"),
    MONTHLY("MONTHLY", "按月付款"),
    QUARTERLY("QUARTERLY", "按季付款"),
    YEARLY("YEARLY", "按年付款"),
    CUSTOM("CUSTOM", "自定义付款");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    PaymentTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

