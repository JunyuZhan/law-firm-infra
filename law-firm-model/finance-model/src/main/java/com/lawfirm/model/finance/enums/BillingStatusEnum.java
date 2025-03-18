package com.lawfirm.model.finance.enums;

import lombok.Getter;

/**
 * 账单状态枚举
 */
@Getter
public enum BillingStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待付款"),
    CONFIRMED(2, "已确认"),
    PAID(3, "已付款"),
    OVERDUE(4, "已逾期"),
    CANCELLED(5, "已取消");

    private final Integer code;
    private final String desc;

    BillingStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static BillingStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BillingStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 