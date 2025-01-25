package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum CaseStatusEnum implements BaseEnum<String> {
    DRAFT("草稿"),
    PENDING("待处理"),
    IN_PROGRESS("处理中"),
    SUSPENDED("已暂停"),
    COMPLETED("已完成"),
    CLOSED("已关闭"),
    ARCHIVED("已归档"),
    CANCELLED("已取消");

    private final String description;

    CaseStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 