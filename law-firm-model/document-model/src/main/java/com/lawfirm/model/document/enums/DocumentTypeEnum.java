package com.lawfirm.model.document.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum DocumentTypeEnum implements BaseEnum<String> {
    
    CONTRACT("CONTRACT", "合同文件"),
    LEGAL_OPINION("LEGAL_OPINION", "法律意见书"),
    COURT_DOC("COURT_DOC", "法院文书"),
    EVIDENCE("EVIDENCE", "证据材料"),
    MEETING_RECORD("MEETING_RECORD", "会议记录"),
    CORRESPONDENCE("CORRESPONDENCE", "往来函件"),
    INTERNAL_DOC("INTERNAL_DOC", "内部文件"),
    TEMPLATE("TEMPLATE", "文档模板"),
    OTHER("OTHER", "其他文件");

    private final String value;
    private final String description;

    DocumentTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 