package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 金融诈骗罪
 */
@Getter
public enum FinancialFraudCharge implements BaseEnum<String> {
    FUND_RAISING_FRAUD("集资诈骗罪", "030501"),
    LOAN_FRAUD("贷款诈骗罪", "030502"),
    BILL_FRAUD("票据诈骗罪", "030503"),
    FINANCIAL_CERTIFICATE_FRAUD("金融凭证诈骗罪", "030504"),
    LETTER_OF_CREDIT_FRAUD("信用证诈骗罪", "030505"),
    CREDIT_CARD_FRAUD("信用卡诈骗罪", "030506"),
    SECURITIES_FRAUD("有价证券诈骗罪", "030507"),
    INSURANCE_FRAUD("保险诈骗罪", "030508");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.FINANCIAL_FRAUD;

    FinancialFraudCharge(String description, String code) {
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