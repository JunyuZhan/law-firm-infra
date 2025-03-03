package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum InvoiceTypeEnum implements BaseEnum<String> {
    
    VAT_SPECIAL("VAT_SPECIAL", "增值税专用发票"),
    VAT_NORMAL("VAT_NORMAL", "增值税普通发票"),
    VAT_ELECTRONIC("VAT_ELECTRONIC", "增值税电子发票"),
    RECEIPT("RECEIPT", "收据");

    private final String value;
    private final String description;

    InvoiceTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 