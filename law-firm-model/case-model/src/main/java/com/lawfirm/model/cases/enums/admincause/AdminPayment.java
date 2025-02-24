package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政给付三级案由
 */
@Getter
public enum AdminPayment implements BaseEnum<String> {
    PENSION_PAYMENT("给付抚恤金", "010801"),
    BASIC_PENSION("给付基本养老金", "010802"),
    MEDICAL_INSURANCE_PAYMENT("给付基本医疗保险金", "010803"),
    UNEMPLOYMENT_INSURANCE_PAYMENT("给付失业保险金", "010804"),
    WORK_INJURY_INSURANCE_PAYMENT("给付工伤保险金", "010805"),
    MATERNITY_INSURANCE_PAYMENT("给付生育保险金", "010806"),
    MINIMUM_LIVING_ALLOWANCE("给付最低生活保障金", "010807");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_PAYMENT;

    AdminPayment(String description, String code) {
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