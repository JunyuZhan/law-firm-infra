package com.lawfirm.model.contract.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 合同状态枚举
 */
@Getter
public enum ContractStatusEnum {
    
    DRAFT("DRAFT", "草稿"),
    PENDING_REVIEW("PENDING_REVIEW", "待审核"),
    REVIEWING("REVIEWING", "审核中"),
    REJECTED("REJECTED", "已驳回"),
    APPROVED("APPROVED", "已审核"),
    SIGNED("SIGNED", "已签署"),
    EXECUTING("EXECUTING", "执行中"),
    COMPLETED("COMPLETED", "已完成"),
    TERMINATED("TERMINATED", "已终止"),
    ARCHIVED("ARCHIVED", "已归档");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    ContractStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

