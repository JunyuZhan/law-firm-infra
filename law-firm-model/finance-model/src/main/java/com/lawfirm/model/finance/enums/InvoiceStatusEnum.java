package com.lawfirm.model.finance.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatusEnum {
    DRAFT("草稿"),
    PENDING("待开具"),
    ISSUED("已开具"),
    CANCELLED("已作废"),
    INVALID("已失效");

    private final String description;

    InvoiceStatusEnum(String description) {
        this.description = description;
    }
} 