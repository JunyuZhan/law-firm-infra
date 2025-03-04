package com.lawfirm.model.ai.enums;

/**
 * 反馈类型枚举
 */
public enum FeedbackTypeEnum {
    
    QUESTION("question", "问题反馈"),
    SUGGESTION("suggestion", "建议反馈"),
    BUG("bug", "缺陷反馈"),
    EXPERIENCE("experience", "体验反馈"),
    CONTENT("content", "内容反馈"),
    OTHER("other", "其他");
    
    private final String code;
    private final String desc;
    
    FeedbackTypeEnum(String code, String desc) {
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
    public static FeedbackTypeEnum getByCode(String code) {
        for (FeedbackTypeEnum item : FeedbackTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return OTHER;
    }
} 