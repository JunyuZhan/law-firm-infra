package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.EmployeeConstant;

/**
 * 合同类型枚举
 */
public enum ContractTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 固定期限
     */
    FIXED_TERM(EmployeeConstant.ContractType.FIXED_TERM, "固定期限"),

    /**
     * 无固定期限
     */
    INDEFINITE(EmployeeConstant.ContractType.INDEFINITE, "无固定期限"),

    /**
     * 实习协议
     */
    INTERNSHIP(EmployeeConstant.ContractType.INTERNSHIP, "实习协议"),

    /**
     * 劳务协议
     */
    LABOR(EmployeeConstant.ContractType.LABOR, "劳务协议");

    private final Integer value;
    private final String description;

    ContractTypeEnum(Integer value, String description) {
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

    public static ContractTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (ContractTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 