package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政强制措施三级案由
 */
@Getter
public enum AdminCoerciveMeasures implements BaseEnum<String> {
    RESTRICT_PERSONAL_FREEDOM("限制人身自由", "010201"),
    SEAL_PREMISES("查封场所、设施或者财物", "010202"),
    SEIZE_PROPERTY("扣押财物", "010203"),
    FREEZE_DEPOSITS("冻结存款、汇款", "010204"),
    FREEZE_SECURITIES("冻结资金、证券", "010205"),
    COMPULSORY_DETOX("强制隔离戒毒", "010206"),
    DETENTION("留置", "010207"),
    PROTECTIVE_RESTRAINT("采取保护性约束措施", "010208");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_COERCIVE_MEASURES;

    AdminCoerciveMeasures(String description, String code) {
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