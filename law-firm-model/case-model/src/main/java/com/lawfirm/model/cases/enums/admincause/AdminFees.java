package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政收费三级案由
 */
@Getter
public enum AdminFees implements BaseEnum<String> {
    CERTIFICATE_FEE("证照费", "011201"),
    VEHICLE_TOLL("车辆通行费", "011202"),
    ENTERPRISE_REGISTRATION_FEE("企业注册登记费", "011203"),
    REAL_ESTATE_REGISTRATION_FEE("不动产登记费", "011204"),
    SHIP_REGISTRATION_FEE("船舶登记费", "011205"),
    EXAM_FEE("考试考务费", "011206");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_FEES;

    AdminFees(String description, String code) {
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