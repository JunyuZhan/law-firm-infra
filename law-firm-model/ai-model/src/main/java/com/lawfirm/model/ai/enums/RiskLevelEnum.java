package com.lawfirm.model.ai.enums;

/**
 * 风险等级枚举
 */
public enum RiskLevelEnum {
    
    HIGH("high", "高风险", 3),
    MEDIUM("medium", "中风险", 2),
    LOW("low", "低风险", 1),
    NONE("none", "无风险", 0);
    
    private final String code;
    private final String desc;
    private final Integer level;
    
    RiskLevelEnum(String code, String desc, Integer level) {
        this.code = code;
        this.desc = desc;
        this.level = level;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    /**
     * 根据code获取枚举值
     */
    public static RiskLevelEnum getByCode(String code) {
        for (RiskLevelEnum item : RiskLevelEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return NONE;
    }
    
    /**
     * 根据分值获取风险等级
     * 
     * @param score 分值(0-100)
     * @return 风险等级
     */
    public static RiskLevelEnum getByScore(Double score) {
        if (score == null) {
            return NONE;
        }
        
        if (score >= 70) {
            return HIGH;
        } else if (score >= 40) {
            return MEDIUM;
        } else if (score > 0) {
            return LOW;
        } else {
            return NONE;
        }
    }
} 