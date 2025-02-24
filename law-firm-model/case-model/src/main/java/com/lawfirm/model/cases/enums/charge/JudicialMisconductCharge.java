package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 枉法裁判罪
 */
@Getter
public enum JudicialMisconductCharge implements BaseEnum<String> {
    PERVERTING_LAW("徇私枉法罪", "090401"),
    CIVIL_ADMINISTRATIVE_PERVERSION("民事、行政枉法裁判罪", "090402"),
    NEGLIGENT_EXECUTION("执行判决、裁定失职罪", "090403"),
    ABUSIVE_EXECUTION("执行判决、裁定滥用职权罪", "090404"),
    PERVERTING_ARBITRATION("枉法仲裁罪", "090405"),
    ILLEGAL_RELEASE("私放在押人员罪", "090406"),
    NEGLIGENT_ESCAPE("失职致使在押人员脱逃罪", "090407"),
    ILLEGAL_COMMUTATION("徇私舞弊减刑、假释、暂予监外执行罪", "090408"),
    REFUSING_CRIMINAL_CASE("徇私舞弊不移交刑事案件罪", "090409");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.JUDICIAL_MISCONDUCT;

    JudicialMisconductCharge(String description, String code) {
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