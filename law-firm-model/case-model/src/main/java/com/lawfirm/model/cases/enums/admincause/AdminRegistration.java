package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政登记三级案由
 */
@Getter
public enum AdminRegistration implements BaseEnum<String> {
    HOUSE_OWNERSHIP("房屋所有权登记", "010601"),
    COLLECTIVE_LAND_OWNERSHIP("集体土地所有权登记", "010602"),
    FOREST_OWNERSHIP("森林、林木所有权登记", "010603"),
    MINING_RIGHTS("矿业权登记", "010604"),
    LAND_CONTRACT("土地承包经营权登记", "010605"),
    CONSTRUCTION_LAND_USE("建设用地使用权登记", "010606"),
    HOMESTEAD_USE("宅基地使用权登记", "010607"),
    SEA_AREA_USE("海域使用权登记", "010608"),
    WATER_PROJECT("水利工程登记", "010609"),
    RESIDENCE_RIGHT("居住权登记", "010610"),
    EASEMENT("地役权登记", "010611"),
    REAL_ESTATE_MORTGAGE("不动产抵押登记", "010612"),
    CHATTEL_MORTGAGE("动产抵押登记", "010613"),
    PLEDGE("质押登记", "010614"),
    VEHICLE_OWNERSHIP("机动车所有权登记", "010615"),
    SHIP_OWNERSHIP("船舶所有权登记", "010616"),
    HOUSEHOLD("户籍登记", "010617"),
    MARRIAGE("婚姻登记", "010618"),
    ADOPTION("收养登记", "010619"),
    TAX("税务登记", "010620");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_REGISTRATION;

    AdminRegistration(String description, String code) {
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