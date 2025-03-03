package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 账户类型枚举
 */
@Getter
public enum AccountTypeEnum implements BaseEnum<String> {
    
    CORPORATE("CORPORATE", "对公账户"),
    PERSONAL("PERSONAL", "对私账户"),
    PETTY_CASH("PETTY_CASH", "备用金账户"),
    SETTLEMENT("SETTLEMENT", "结算账户"),
    SPECIAL("SPECIAL", "专用账户");

    private final String value;
    private final String description;

    AccountTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 