package com.lawfirm.model.contract.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 审批状态枚举
 */
@Getter
public enum ApprovalStatusEnum {
    
    PENDING("PENDING", "待审批"),
    APPROVED("APPROVED", "已通过"),
    REJECTED("REJECTED", "已驳回"),
    WITHDRAWN("WITHDRAWN", "已撤回");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    ApprovalStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

