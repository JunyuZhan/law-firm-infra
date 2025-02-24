package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 非暴力侵犯财产罪
 */
@Getter
public enum NonViolentPropertyCharge implements BaseEnum<String> {
    THEFT("盗窃罪", "050201"),
    FRAUD("诈骗罪", "050202"),
    EMBEZZLEMENT("侵占罪", "050203"),
    MALICIOUS_DAMAGE("故意毁坏财物罪", "050204"),
    BUSINESS_DISRUPTION("破坏生产经营罪", "050205"),
    WAGE_NONPAYMENT("拒不支付劳动报酬罪", "050206");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.NON_VIOLENT_PROPERTY;

    NonViolentPropertyCharge(String description, String code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 