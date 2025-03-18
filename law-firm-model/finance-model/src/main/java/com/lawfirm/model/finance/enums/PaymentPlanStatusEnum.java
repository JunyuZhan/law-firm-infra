package com.lawfirm.model.finance.enums;

import lombok.Getter;

/**
 * 付款计划状态枚举
 */
@Getter
public enum PaymentPlanStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待付款"),
    PAID(2, "已付款"),
    CANCELLED(3, "已取消"),
    OVERDUE(4, "已逾期");

    private final Integer code;
    private final String desc;

    PaymentPlanStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PaymentPlanStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PaymentPlanStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 