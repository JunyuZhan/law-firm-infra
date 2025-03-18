package com.lawfirm.model.finance.enums;

import lombok.Getter;

/**
 * 费用状态枚举
 */
@Getter
public enum FeeStatusEnum {
    
    DRAFT(0, "草稿"),
    PENDING(1, "待审批"),
    APPROVED(2, "已通过"),
    REJECTED(3, "已拒绝"),
    PAID(4, "已支付"),
    CANCELLED(5, "已取消");

    private final Integer code;
    private final String desc;

    FeeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FeeStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (FeeStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 