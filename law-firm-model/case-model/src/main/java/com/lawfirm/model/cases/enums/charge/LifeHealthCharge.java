package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵犯生命健康权利罪
 */
@Getter
public enum LifeHealthCharge implements BaseEnum<String> {
    INTENTIONAL_HOMICIDE("故意杀人罪", "040101"),
    NEGLIGENT_HOMICIDE("过失致人死亡罪", "040102"),
    INTENTIONAL_INJURY("故意伤害罪", "040103"),
    ORGAN_TRAFFICKING("组织出卖人体器官罪", "040104"),
    NEGLIGENT_INJURY("过失致人重伤罪", "040105");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.LIFE_HEALTH;

    LifeHealthCharge(String description, String code) {
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