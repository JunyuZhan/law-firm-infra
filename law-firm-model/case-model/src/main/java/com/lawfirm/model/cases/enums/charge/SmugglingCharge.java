package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 走私罪
 */
@Getter
public enum SmugglingCharge implements BaseEnum<String> {
    SMUGGLING_WEAPONS("走私武器、弹药罪", "030201"),
    SMUGGLING_NUCLEAR_MATERIALS("走私核材料罪", "030202"),
    SMUGGLING_COUNTERFEIT_CURRENCY("走私假币罪", "030203"),
    SMUGGLING_CULTURAL_RELICS("走私文物罪", "030204"),
    SMUGGLING_PRECIOUS_METALS("走私贵重金属罪", "030205"),
    SMUGGLING_RARE_ANIMALS("走私珍贵动物、珍贵动物制品罪", "030206"),
    SMUGGLING_PRECIOUS_PLANTS("走私珍贵植物、珍贵植物制品罪", "030207"),
    SMUGGLING_ORDINARY_GOODS("走私普通货物、物品罪", "030208"),
    SMUGGLING_SOLID_WASTE("走私固体废物罪", "030209"),
    SMUGGLING_PROHIBITED_MATERIALS("走私禁止进出口的其他货物、物品罪", "030210");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.SMUGGLING;

    SmugglingCharge(String description, String code) {
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