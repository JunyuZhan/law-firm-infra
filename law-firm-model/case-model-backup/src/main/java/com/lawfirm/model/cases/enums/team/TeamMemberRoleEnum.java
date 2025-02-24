package com.lawfirm.model.cases.enums.team;

/**
 * 团队成员角色枚举
 */
public enum TeamMemberRoleEnum {
    
    LEADER("LEADER", "主办律师"),
    ASSISTANT("ASSISTANT", "助理律师"),
    PARTNER("PARTNER", "合伙人"),
    CONSULTANT("CONSULTANT", "顾问"),
    PARALEGAL("PARALEGAL", "律师助理"),
    INTERN("INTERN", "实习生"),
    OTHER("OTHER", "其他");

    private final String code;
    private final String desc;

    TeamMemberRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
} 