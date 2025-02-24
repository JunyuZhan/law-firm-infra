package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政许可三级案由
 */
@Getter
public enum AdminLicense implements BaseEnum<String> {
    BUSINESS_REGISTRATION("工商登记", "010401"),
    SOCIAL_ORG_REGISTRATION("社会团体登记", "010402"),
    DRIVING_LICENSE("颁发机动车驾驶证", "010403"),
    FRANCHISE_LICENSE("特许经营许可", "010404"),
    CONSTRUCTION_PLANNING("建设工程规划许可", "010405"),
    CONSTRUCTION_PERMIT("建筑工程施工许可", "010406"),
    MINERAL_RESOURCES("矿产资源许可", "010407"),
    DRUG_REGISTRATION("药品注册许可", "010408"),
    MEDICAL_DEVICE("医疗器械许可", "010409"),
    PROFESSIONAL_QUALIFICATION("执业资格许可", "010410");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_LICENSE;

    AdminLicense(String description, String code) {
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