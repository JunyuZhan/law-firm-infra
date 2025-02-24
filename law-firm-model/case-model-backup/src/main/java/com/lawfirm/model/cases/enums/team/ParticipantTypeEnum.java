package com.lawfirm.model.cases.enums.team;

/**
 * 案件参与者类型枚举
 */
public enum ParticipantTypeEnum {
    PLAINTIFF("原告"),
    DEFENDANT("被告"),
    THIRD_PARTY("第三人"),
    WITNESS("证人"),
    EXPERT("专家"),
    OTHER("其他");

    private final String description;

    ParticipantTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 