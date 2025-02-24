package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 妨害公共卫生罪
 */
@Getter
public enum PublicHealthCharge implements BaseEnum<String> {
    SPREADING_DISEASE("传染病防治失职罪", "061001"),
    MEDICAL_ACCIDENT("医疗事故罪", "061002"),
    ILLEGAL_BLOOD_COLLECTION("非法采集、供应血液、制作、供应血液制品罪", "061003"),
    TAINTED_BLOOD("采集、供应血液、制作、供应血液制品事故罪", "061004"),
    ILLEGAL_ORGAN_TRANSPLANT("非法组织人体器官移植罪", "061005"),
    MEDICAL_WASTE("非法处置传染病病原体、医疗废物罪", "061006"),
    ANIMAL_EPIDEMIC("动物疫情防治失职罪", "061007"),
    ILLEGAL_ANIMAL_QUARANTINE("违反动物防疫、检疫规定罪", "061008");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.PUBLIC_HEALTH;

    PublicHealthCharge(String description, String code) {
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