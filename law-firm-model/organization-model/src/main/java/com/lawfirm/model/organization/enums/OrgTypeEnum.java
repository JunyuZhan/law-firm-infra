package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum OrgTypeEnum implements BaseEnum<String> {
    
    HEAD_OFFICE("HEAD_OFFICE", "总部"),
    BRANCH("BRANCH", "分支机构"),
    DEPARTMENT("DEPARTMENT", "部门"),
    TEAM("TEAM", "团队"),
    PROJECT("PROJECT", "项目组"),
    VIRTUAL("VIRTUAL", "虚拟组织");

    private final String value;
    private final String description;

    OrgTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 