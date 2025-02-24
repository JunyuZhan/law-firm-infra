package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 破坏税收征管罪
 */
@Getter
public enum TaxCollectionCharge implements BaseEnum<String> {
    TAX_EVASION("逃税罪", "030601"),
    REFUSING_TAX_PAYMENT("抗税罪", "030602"),
    FALSE_TAX_RECEIPT("虚开增值税专用发票、用于骗取出口退税、抵扣税款发票罪", "030603"),
    FORGING_TAX_RECEIPT("伪造、出售伪造的增值税专用发票罪", "030604"),
    ILLEGAL_TAX_RECEIPT("非法出售增值税专用发票罪", "030605"),
    STEALING_TAX_RECEIPT("非法购买增值税专用发票、购买伪造的增值税专用发票罪", "030606"),
    FORGING_TAX_DOCUMENTS("伪造、变造、买卖国家机关公文、证件、印章罪", "030607");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.TAX_COLLECTION;

    TaxCollectionCharge(String description, String code) {
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