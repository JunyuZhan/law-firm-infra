package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 发票状态枚举
 */
@Getter
public enum InvoiceStatusEnum implements BaseEnum<String> {
    
    DRAFT("DRAFT", "草稿"),
    PENDING("PENDING", "待开票"),
    ISSUED("ISSUED", "已开票"),
    CANCELLED("CANCELLED", "已作废");

    private final String value;
    private final String description;

    InvoiceStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 