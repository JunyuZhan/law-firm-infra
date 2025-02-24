package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 战时违抗命令罪
 */
@Getter
public enum WartimeOffensesCharge implements BaseEnum<String> {
    WARTIME_DISOBEDIENCE("战时违抗命令罪", "070401"),
    WARTIME_SURRENDER("战时自首投降罪", "070402"),
    WARTIME_COWARDICE("战时临阵脱逃罪", "070403"),
    WARTIME_WEAPON_SURRENDER("战时遗弃武器装备罪", "070404"),
    WARTIME_MILITARY_SECRETS("战时故意泄露军事秘密罪", "070405"),
    WARTIME_FALSE_MILITARY_INFO("战时造谣惑众罪", "070406"),
    WARTIME_PROVIDING_ENEMY_INFO("战时为敌人提供军事情报罪", "070407"),
    WARTIME_ASSISTING_ENEMY("战时帮助敌人罪", "070408");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.WARTIME_OFFENSES;

    WartimeOffensesCharge(String description, String code) {
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