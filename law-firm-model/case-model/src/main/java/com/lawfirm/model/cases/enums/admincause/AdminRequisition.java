package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政征收征用三级案由
 */
@Getter
public enum AdminRequisition implements BaseEnum<String> {
    HOUSE_REQUISITION("征收或者征用房屋", "010501"),
    LAND_REQUISITION("征收或者征用土地", "010502"),
    MOVABLE_PROPERTY_REQUISITION("征收或者征用动产", "010503");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_REQUISITION;

    AdminRequisition(String description, String code) {
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