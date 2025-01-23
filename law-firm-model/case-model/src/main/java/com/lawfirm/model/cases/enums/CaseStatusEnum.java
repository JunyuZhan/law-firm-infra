package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum CaseStatusEnum implements BaseEnum<String> {
    
    DRAFT("DRAFT", "草稿"),
    PENDING("PENDING", "待处理"),
    IN_PROGRESS("IN_PROGRESS", "进行中"),
    SUSPENDED("SUSPENDED", "暂停"),
    COMPLETED("COMPLETED", "已完成"),
    CLOSED("CLOSED", "已结案"),
    ARCHIVED("ARCHIVED", "已归档");

    private final String value;
    private final String description;

    CaseStatusEnum(String value, String description) {
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