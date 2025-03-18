package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 费用类型枚举 - 财务模块
 */
@Getter
public enum FeeTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 法律服务费
     */
    LEGAL_SERVICE(1, "法律服务费"),
    
    /**
     * 法律咨询费
     */
    CONSULTATION(2, "法律咨询费"),
    
    /**
     * 法律顾问费
     */
    RETAINER(3, "法律顾问费"),
    
    /**
     * 代理费
     */
    AGENCY(4, "代理费"),
    
    /**
     * 成功费
     */
    SUCCESS(5, "成功费"),
    
    /**
     * 文件起草费
     */
    DOCUMENT_DRAFTING(6, "文件起草费"),
    
    /**
     * 合同审查费
     */
    CONTRACT_REVIEW(7, "合同审查费"),
    
    /**
     * 尽职调查费
     */
    DUE_DILIGENCE(8, "尽职调查费"),
    
    /**
     * 法律意见书费
     */
    LEGAL_OPINION(9, "法律意见书费"),
    
    /**
     * 案件管理费
     */
    CASE_MANAGEMENT(10, "案件管理费"),
    
    /**
     * 差旅费
     */
    TRAVEL_EXPENSE(11, "差旅费"),
    
    /**
     * 垫付款
     */
    ADVANCED_PAYMENT(12, "垫付款"),
    
    /**
     * 其他
     */
    OTHER(99, "其他");

    private final Integer value;
    private final String description;

    FeeTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 根据值获取枚举
     */
    public static FeeTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (FeeTypeEnum feeType : values()) {
            if (feeType.value.equals(value)) {
                return feeType;
            }
        }
        return null;
    }
} 