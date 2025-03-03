package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 币种枚举
 */
@Getter
public enum CurrencyEnum implements BaseEnum<String> {
    
    CNY("CNY", "人民币"),
    USD("USD", "美元"),
    EUR("EUR", "欧元"),
    GBP("GBP", "英镑"),
    JPY("JPY", "日元"),
    HKD("HKD", "港币"),
    SGD("SGD", "新加坡元"),
    AUD("AUD", "澳元"),
    CAD("CAD", "加拿大元"),
    CHF("CHF", "瑞士法郎");

    private final String value;
    private final String description;

    CurrencyEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 