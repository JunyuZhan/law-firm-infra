package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 军人违反职责罪
 */
@Getter
public enum MilitaryServiceCharge implements BaseEnum<String> {
    INSUBORDINATION("违抗命令罪", "070201"),
    CONCEALING_MILITARY_STRENGTH("隐瞒、谎报军情罪", "070202"),
    WEAPON_SURRENDER("武器装备肇事罪", "070203"),
    MILITARY_FACILITY_ACCIDENT("擅离职守罪", "070204"),
    MILITARY_TRAINING_ACCIDENT("玩忽军事职责罪", "070205"),
    MILITARY_ACCIDENT("过失泄露军事秘密罪", "070206"),
    MILITARY_SECRETS_LEAK("故意泄露军事秘密罪", "070207"),
    MILITARY_EQUIPMENT_DAMAGE("战时违抗命令罪", "070208"),
    MILITARY_SUPPLIES_DAMAGE("遗弃武器装备罪", "070209"),
    MILITARY_PROPERTY_DAMAGE("战时临阵脱逃罪", "070210");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MILITARY_SERVICE;

    MilitaryServiceCharge(String description, String code) {
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