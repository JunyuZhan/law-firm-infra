package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 作战职责罪
 */
@Getter
public enum CombatDutyCharge implements BaseEnum<String> {
    DISOBEYING_ORDERS("战时违抗命令罪", "100101"),
    CONCEALING_MILITARY_INFO("隐瞒、谎报军情罪", "100102"),
    REFUSING_MILITARY_ORDERS("拒传、假传军令罪", "100103"),
    SURRENDER("投降罪", "100104"),
    DESERTION("战时临阵脱逃罪", "100105"),
    DERELICTION_OF_DUTY("擅离、玩忽军事职守罪", "100106"),
    OBSTRUCTING_DUTY("阻碍执行军事职务罪", "100107"),
    INSTRUCTING_VIOLATION("指使部属违反职责罪", "100108"),
    PASSIVE_COMBAT("违令作战消极罪", "100109"),
    REFUSING_ASSISTANCE("拒不救援友邻部队罪", "100110"),
    MILITARY_DEFECTION("军人叛逃罪", "100111"),
    SPREADING_RUMORS("战时造谣惑众罪", "100112"),
    SELF_INJURY("战时自伤罪", "100113"),
    LEAVING_POST("逃离部队罪", "100114");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.COMBAT_DUTY;

    CombatDutyCharge(String description, String code) {
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