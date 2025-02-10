package com.lawfirm.model.finance.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodEnum {
    CASH("现金"),
    BANK_TRANSFER("银行转账"),
    WECHAT("微信支付"),
    ALIPAY("支付宝"),
    POS("POS机"),
    CHECK("支票"),
    OTHER("其他");

    private final String description;

    PaymentMethodEnum(String description) {
        this.description = description;
    }
} 