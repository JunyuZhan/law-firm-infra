package com.lawfirm.model.finance.enums;

import lombok.Getter;

/**
 * 应收款状态枚举
 */
@Getter
public enum ReceivableStatusEnum {
    
    PENDING(0, "待收款"),
    PARTIAL(1, "部分收款"),
    COMPLETED(2, "已收款"),
    OVERDUE(3, "已逾期"),
    CANCELLED(4, "已取消");

    private final Integer code;
    private final String desc;

    ReceivableStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ReceivableStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ReceivableStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 