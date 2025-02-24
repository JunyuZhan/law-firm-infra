package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 巨额财产来源不明罪
 */
@Getter
public enum UnexplainedAssetsCharge implements BaseEnum<String> {
    UNEXPLAINED_ASSETS("巨额财产来源不明罪", "080301");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.UNEXPLAINED_ASSETS;

    UnexplainedAssetsCharge(String description, String code) {
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