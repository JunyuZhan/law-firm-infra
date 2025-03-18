package com.lawfirm.model.finance.enums;

import lombok.Getter;

/**
 * 付款状态枚举
 */
@Getter
public enum PaymentStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待付款"),
    CONFIRMED(2, "已确认"),
    PAID(3, "已付款"),
    CANCELLED(4, "已取消");

    private final Integer code;
    private final String desc;

    PaymentStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PaymentStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (PaymentStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 