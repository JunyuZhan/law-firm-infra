package com.lawfirm.model.contract.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 合同优先级枚举
 */
@Getter
public enum ContractPriorityEnum {
    
    HIGH("HIGH", "高"),
    MEDIUM("MEDIUM", "中"),
    LOW("LOW", "低");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    ContractPriorityEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

