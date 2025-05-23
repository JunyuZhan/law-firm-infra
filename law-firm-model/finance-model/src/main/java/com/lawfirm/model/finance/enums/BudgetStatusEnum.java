package com.lawfirm.model.finance.enums;

import lombok.Getter;

/**
 * 预算状态枚举
 */
@Getter
public enum BudgetStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待审批"),
    APPROVED(2, "已审批"),
    REJECTED(3, "已驳回"),
    CANCELLED(4, "已取消");

    private final Integer code;
    private final String desc;

    BudgetStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static BudgetStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BudgetStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 