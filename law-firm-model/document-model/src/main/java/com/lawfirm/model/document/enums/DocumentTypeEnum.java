package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档类型枚举
 */
@Getter
public enum DocumentTypeEnum {
    
    CONTRACT("CONTRACT", "合同文档"),
    LEGAL_OPINION("LEGAL_OPINION", "法律意见书"),
    CASE_DOCUMENT("CASE_DOCUMENT", "案件文书"),
    MEETING_MINUTES("MEETING_MINUTES", "会议纪要"),
    RESEARCH_REPORT("RESEARCH_REPORT", "研究报告"),
    INTERNAL_DOCUMENT("INTERNAL_DOCUMENT", "内部文档");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    DocumentTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
} 