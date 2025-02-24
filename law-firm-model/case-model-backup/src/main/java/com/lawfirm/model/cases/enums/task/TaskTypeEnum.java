package com.lawfirm.model.cases.enums.task;

/**
 * 任务类型枚举
 */
public enum TaskTypeEnum {
    RESEARCH("调研"),
    DOCUMENT_REVIEW("文件审查"),
    DRAFT("起草"),
    MEETING("会议"),
    COURT_APPEARANCE("出庭"),
    CLIENT_COMMUNICATION("客户沟通"),
    OTHER("其他");

    private final String description;

    TaskTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 