package com.lawfirm.model.cases.enums.note;

/**
 * 笔记类型枚举
 */
public enum NoteTypeEnum {
    
    MEETING("MEETING", "会议记录"),
    PHONE("PHONE", "电话记录"),
    RESEARCH("RESEARCH", "调研笔记"),
    EVIDENCE("EVIDENCE", "证据分析"),
    STRATEGY("STRATEGY", "策略分析"),
    SUMMARY("SUMMARY", "案件总结"),
    OTHER("OTHER", "其他");

    private final String code;
    private final String desc;

    NoteTypeEnum(String code, String desc) {
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