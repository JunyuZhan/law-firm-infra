package com.lawfirm.model.cases.enums;

/**
 * 笔记类型枚举
 */
public enum NoteTypeEnum {
    MEETING_MINUTES("会议记录"),
    CASE_ANALYSIS("案件分析"),
    CLIENT_COMMUNICATION("客户沟通"),
    RESEARCH_NOTE("研究笔记"),
    STRATEGY_PLANNING("策略规划"),
    GENERAL("一般记录");

    private final String description;

    NoteTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 