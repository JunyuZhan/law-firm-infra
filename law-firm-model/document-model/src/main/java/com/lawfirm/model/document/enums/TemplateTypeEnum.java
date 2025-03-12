package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档模板类型枚举
 */
@Getter
public enum TemplateTypeEnum {

    /**
     * 合同模板
     */
    CONTRACT("CONTRACT", "合同模板"),

    /**
     * 协议模板
     */
    AGREEMENT("AGREEMENT", "协议模板"),

    /**
     * 法律文书模板
     */
    LEGAL_DOC("LEGAL_DOC", "法律文书模板"),

    /**
     * 诉讼文书模板
     */
    LITIGATION("LITIGATION", "诉讼文书模板"),

    /**
     * 公函模板
     */
    OFFICIAL_LETTER("OFFICIAL_LETTER", "公函模板"),

    /**
     * 报告模板
     */
    REPORT("REPORT", "报告模板"),

    /**
     * 其他模板
     */
    OTHER("OTHER", "其他模板");

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

    TemplateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据编码获取枚举
     */
    public static TemplateTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (TemplateTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 