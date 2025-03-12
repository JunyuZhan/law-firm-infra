package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档类型枚举
 */
@Getter
public enum DocumentTypeEnum {

    /**
     * 合同文档
     */
    CONTRACT("CONTRACT", "合同文档"),

    /**
     * 案件文档
     */
    CASE("CASE", "案件文档"),

    /**
     * 法律文章
     */
    ARTICLE("ARTICLE", "法律文章"),

    /**
     * 法律意见书
     */
    LEGAL_OPINION("LEGAL_OPINION", "法律意见书"),

    /**
     * 诉讼文书
     */
    LITIGATION("LITIGATION", "诉讼文书"),

    /**
     * 会议纪要
     */
    MEETING_MINUTES("MEETING_MINUTES", "会议纪要"),

    /**
     * 工作报告
     */
    WORK_REPORT("WORK_REPORT", "工作报告"),

    /**
     * 研究报告
     */
    RESEARCH_REPORT("RESEARCH_REPORT", "研究报告"),

    /**
     * 其他文档
     */
    OTHER("OTHER", "其他文档");

    /**
     * 类型编码
     */
    @EnumValue
    @JsonValue
    private final String code;

    /**
     * 类型名称
     */
    private final String name;

    DocumentTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据编码获取枚举
     */
    public static DocumentTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (DocumentTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 