package com.lawfirm.model.personnel.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.personnel.constant.PersonnelConstants;

/**
 * 合同类型枚举
 */
public enum ContractTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 固定期限
     */
    FIXED_TERM(1, "固定期限"),

    /**
     * 无固定期限
     */
    INDEFINITE(2, "无固定期限"),

    /**
     * 实习协议
     */
    INTERNSHIP(3, "实习协议"),

    /**
     * 劳务协议
     */
    LABOR(PersonnelConstants.ContractType.LABOR, "劳务协议"),
    
    /**
     * 服务合同
     */
    SERVICE(PersonnelConstants.ContractType.SERVICE, "服务合同"),
    
    /**
     * 保密协议
     */
    CONFIDENTIAL(PersonnelConstants.ContractType.CONFIDENTIAL, "保密协议"),
    
    /**
     * 竞业限制协议
     */
    NON_COMPETE(PersonnelConstants.ContractType.NON_COMPETE, "竞业限制协议");

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