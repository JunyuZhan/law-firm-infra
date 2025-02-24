package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 监管渎职罪
 */
@Getter
public enum RegulatoryNegligenceCharge implements BaseEnum<String> {
    COMPANY_SECURITIES_ABUSE("滥用管理公司、证券职权罪", "090601"),
    TAX_EVASION_NEGLIGENCE("徇私舞弊不征、少征税款罪", "090602"),
    TAX_REFUND_FRAUD("徇私舞弊发售发票、抵扣税款、出口退税罪", "090603"),
    ILLEGAL_TAX_REFUND("违法提供出口退税凭证罪", "090604"),
    CONTRACT_FRAUD_NEGLIGENCE("国家机关工作人员签订、履行合同失职被骗罪", "090605"),
    ILLEGAL_LOGGING_PERMIT("违法发放林木采伐许可证罪", "090606"),
    ENVIRONMENTAL_NEGLIGENCE("环境监管失职罪", "090607"),
    FOOD_DRUG_NEGLIGENCE("食品、药品监管渎职罪", "090608"),
    DISEASE_CONTROL_NEGLIGENCE("传染病防治失职罪", "090609"),
    SMUGGLING_NEGLIGENCE("放纵走私罪", "090610"),
    COMMODITY_INSPECTION_FRAUD("商检徇私舞弊罪", "090611"),
    COMMODITY_INSPECTION_NEGLIGENCE("商检失职罪", "090612"),
    QUARANTINE_FRAUD("动植物检疫徇私舞弊罪", "090613"),
    QUARANTINE_NEGLIGENCE("动植物检疫失职罪", "090614"),
    FAKE_PRODUCTS_NEGLIGENCE("放纵制售伪劣商品犯罪行为罪", "090615"),
    CULTURAL_RELIC_NEGLIGENCE("失职造成珍贵文物损毁、流失罪", "090616");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.REGULATORY_NEGLIGENCE;

    RegulatoryNegligenceCharge(String description, String code) {
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