package com.lawfirm.model.cases.enums;

/**
 * 团队成员角色枚举
 */
public enum TeamMemberRoleEnum {
    LEADER("负责人"),
    MANAGER("管理人"),
    MEMBER("成员"),
    ASSISTANT("助理"),
    CONSULTANT("顾问");

    private final String description;

    TeamMemberRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 