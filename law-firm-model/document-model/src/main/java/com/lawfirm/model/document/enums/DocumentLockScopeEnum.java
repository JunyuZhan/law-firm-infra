package com.lawfirm.model.document.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum DocumentLockScopeEnum implements BaseEnum<String> {
    
    FULL("FULL", "全文锁定"),
    PARTIAL("PARTIAL", "部分锁定");

    private final String value;
    private final String description;

    DocumentLockScopeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 