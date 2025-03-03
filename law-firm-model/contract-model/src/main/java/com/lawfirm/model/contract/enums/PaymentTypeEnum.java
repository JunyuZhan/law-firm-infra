package com.lawfirm.model.contract.enums;

import lombok.Getter;

/**
 * 支付类型枚举
 */
@Getter
public enum PaymentTypeEnum {
    ONE_TIME(1, "一次性付款"),
    INSTALLMENT(2, "分期付款"),
    PROGRESS_PAYMENT(3, "按进度付款"),
    RETAINER_FEE(4, "顾问费"),
    SUCCESS_FEE(5, "成功费"),
    HOURLY_RATE(6, "按小时计费"),
    CONTINGENCY_FEE(7, "按风险代理"),
    FREE(8, "免费"),
    LEGAL_AID(9, "法律援助"),
    FLAT_FEE(10, "固定费用"),
    DEPOSIT(11, "预付款");

    private final int code;
    private final String description;

    PaymentTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
} 