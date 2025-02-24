package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 武器装备罪
 */
@Getter
public enum MilitaryEquipmentCharge implements BaseEnum<String> {
    EQUIPMENT_ACCIDENT("武器装备肇事罪"),
    UNAUTHORIZED_CHANGE("擅自改变武器装备编配用途罪"),
    THEFT_ROBBERY("盗窃、抢夺武器装备、军用物资罪"),
    ILLEGAL_SALE("非法出卖、转让武器装备罪"),
    ABANDONMENT("遗弃武器装备罪"),
    LOSING_EQUIPMENT("遗失武器装备罪"),
    ILLEGAL_REAL_ESTATE("擅自出卖、转让军队房地产罪");

    private final String description;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.MILITARY_EQUIPMENT;

    MilitaryEquipmentCharge(String description) {
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}