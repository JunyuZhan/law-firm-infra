package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 账户状态枚举
 */
@Getter
public enum AccountStatusEnum implements BaseEnum<String> {
    
    NORMAL("NORMAL", "正常"),
    FROZEN("FROZEN", "冻结"),
    CANCELLED("CANCELLED", "注销");

    private final String value;
    private final String description;

    AccountStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 