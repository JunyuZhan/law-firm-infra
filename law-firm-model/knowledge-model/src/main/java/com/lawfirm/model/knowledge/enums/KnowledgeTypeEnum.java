package com.lawfirm.model.knowledge.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 知识类型枚举
 */
@Getter
public enum KnowledgeTypeEnum {

    DOCUMENT_TEMPLATE("DOCUMENT_TEMPLATE", "文档模板"),
    WORK_GUIDE("WORK_GUIDE", "工作指南"),
    BUSINESS_STANDARD("BUSINESS_STANDARD", "业务规范"),
    TRAINING_MATERIAL("TRAINING_MATERIAL", "培训资料"),
    EXPERIENCE_SUMMARY("EXPERIENCE_SUMMARY", "经验总结"),
    OTHER("OTHER", "其他");

    @EnumValue
    private final String code;
    private final String desc;

    KnowledgeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据编码获取枚举
     */
    public static KnowledgeTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        
        for (KnowledgeTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        
        return null;
    }
} 