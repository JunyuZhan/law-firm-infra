package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政确认三级案由
 */
@Getter
public enum AdminConfirmation implements BaseEnum<String> {
    PENSION_INSURANCE("基本养老保险资格或者待遇认定", "010701"),
    MEDICAL_INSURANCE("基本医疗保险资格或者待遇认定", "010702"),
    UNEMPLOYMENT_INSURANCE("失业保险资格或者待遇认定", "010703"),
    WORK_INJURY_INSURANCE("工伤保险资格或者待遇认定", "010704"),
    MATERNITY_INSURANCE("生育保险资格或者待遇认定", "010705"),
    MINIMUM_LIVING_SECURITY("最低生活保障资格或者待遇认定", "010706"),
    AFFORDABLE_HOUSING("确认保障性住房分配资格", "010707"),
    ACADEMIC_CERTIFICATE("颁发学位证书或者毕业证书", "010708");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_CONFIRMATION;

    AdminConfirmation(String description, String code) {
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