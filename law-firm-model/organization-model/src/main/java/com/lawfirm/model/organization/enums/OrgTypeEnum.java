package com.lawfirm.model.organization.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 组织类型枚举
 */
@Getter
public enum OrgTypeEnum implements BaseEnum<String> {
    
    HEAD_OFFICE("HEAD_OFFICE", "总部"),
    BRANCH("BRANCH", "分支机构"),
    SUBSIDIARY("SUBSIDIARY", "子公司"),
    DEPARTMENT("DEPARTMENT", "部门"),
    TEAM("TEAM", "团队"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    OrgTypeEnum(String value, String description) {
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