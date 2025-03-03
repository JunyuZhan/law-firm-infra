package com.lawfirm.model.contract.enums;

import lombok.Getter;

/**
 * 合同类型枚举
 */
@Getter
public enum ContractTypeEnum {
    LEGAL_SERVICE(1, "法律服务合同"),
    CONSULTING_SERVICE(2, "咨询服务合同"),
    AGENCY(3, "代理合同"),
    OTHER(4, "其他合同类型");

    private final int code;
    private final String description;

    ContractTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
} 