package com.lawfirm.model.ai.enums;

/**
 * AI操作类型枚举
 */
public enum OperationTypeEnum {
    
    CLASSIFY("classify", "文档分类"),
    EXTRACT("extract", "信息提取"),
    SUMMARIZE("summarize", "生成摘要"),
    ANALYZE("analyze", "文档分析"),
    GENERATE("generate", "文档生成"),
    COMPARE("compare", "文档比较"),
    TRANSLATE("translate", "文档翻译"),
    QA("qa", "问答"),
    RISK_ASSESS("risk_assess", "风险评估"),
    PREDICTION("prediction", "结果预测"),
    RECOMMENDATION("recommendation", "推荐"),
    OTHER("other", "其他");
    
    private final String code;
    private final String desc;
    
    OperationTypeEnum(String code, String desc) {
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
    public static OperationTypeEnum getByCode(String code) {
        for (OperationTypeEnum item : OperationTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return OTHER;
    }
} 