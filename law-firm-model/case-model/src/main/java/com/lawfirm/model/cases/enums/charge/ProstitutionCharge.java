package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 组织、强迫、引诱、容留、介绍卖淫罪
 */
@Getter
public enum ProstitutionCharge implements BaseEnum<String> {
    ORGANIZING_PROSTITUTION("组织卖淫罪", "060801"),
    FORCING_PROSTITUTION("强迫卖淫罪", "060802"),
    LURING_PROSTITUTION("引诱、容留、介绍卖淫罪", "060803"),
    HARBORING_PROSTITUTION("协助组织卖淫罪", "060804");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.PROSTITUTION;

    ProstitutionCharge(String description, String code) {
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