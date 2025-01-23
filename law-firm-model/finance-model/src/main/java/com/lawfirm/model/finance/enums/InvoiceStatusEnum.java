package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BusinessStatusEnum;
import lombok.Getter;

@Getter
public enum InvoiceStatusEnum implements BusinessStatusEnum {
    
    DRAFT("DRAFT", "草稿", "INITIAL", true, false),
    PENDING("PENDING", "待开具", "INITIAL", true, false),
    ISSUED("ISSUED", "已开具", "PROCESSING", false, false),
    CANCELLED("CANCELLED", "已作废", "FINAL", false, true),
    INVALID("INVALID", "已失效", "FINAL", false, true);

    private final String value;
    private final String description;
    private final String group;
    private final boolean editable;
    private final boolean finalStatus;

    InvoiceStatusEnum(String value, String description, String group, boolean editable, boolean finalStatus) {
        this.value = value;
        this.description = description;
        this.group = group;
        this.editable = editable;
        this.finalStatus = finalStatus;
    }

    @Override
    public boolean isFinal() {
        return this.finalStatus;
    }

    @Override
    public boolean isEditable() {
        return this.editable;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public BusinessStatusEnum[] getNextStatus() {
        switch (this) {
            case DRAFT:
                return new BusinessStatusEnum[]{PENDING};
            case PENDING:
                return new BusinessStatusEnum[]{ISSUED};
            case ISSUED:
                return new BusinessStatusEnum[]{CANCELLED};
            default:
                return new BusinessStatusEnum[0];
        }
    }
} 