package com.lawfirm.model.ai.enums;

/**
 * AI模型类型枚举
 */
public enum AiModelTypeEnum {
    
    TEXT_GENERATION("text_generation", "文本生成"),
    DOCUMENT_PROCESSING("document_processing", "文档处理"),
    QUESTION_ANSWERING("question_answering", "问答"),
    DECISION_SUPPORT("decision_support", "决策支持"),
    TRANSLATION("translation", "翻译"),
    SUMMARIZATION("summarization", "摘要"),
    EMBEDDING("embedding", "向量嵌入"),
    CLASSIFICATION("classification", "分类"),
    INFORMATION_EXTRACTION("information_extraction", "信息提取"),
    MULTIMODAL("multimodal", "多模态"),
    OTHER("other", "其他");
    
    private final String code;
    private final String desc;
    
    AiModelTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    /**
     * 根据code获取枚举值
     */
    public static AiModelTypeEnum getByCode(String code) {
        for (AiModelTypeEnum item : AiModelTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return OTHER;
    }
} 