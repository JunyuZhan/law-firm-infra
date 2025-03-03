package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 支付方式枚举
 */
@Getter
public enum PaymentMethodEnum implements BaseEnum<String> {
    
    CASH("CASH", "现金"),
    BANK_TRANSFER("BANK_TRANSFER", "银行转账"),
    WECHAT("WECHAT", "微信支付"),
    ALIPAY("ALIPAY", "支付宝"),
    POS("POS", "POS机"),
    CHECK("CHECK", "支票"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    PaymentMethodEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 