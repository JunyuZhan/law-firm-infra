package com.lawfirm.model.contract.enums;

import lombok.Getter;

/**
 * 合同状态枚举
 */
@Getter
public enum ContractStatusEnum {
    DRAFT(1, "草稿"),
    UNDER_REVIEW(2, "审核中"),
    EFFECTIVE(3, "已生效"),
    TERMINATED(4, "已终止"),
    EXPIRED(5, "已到期");

    private final int code;
    private final String description;

    ContractStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
} 