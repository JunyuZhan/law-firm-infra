package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政征缴三级案由
 */
@Getter
public enum AdminCollection implements BaseEnum<String> {
    TAX_COLLECTION("征缴税款", "011001"),
    SOCIAL_MAINTENANCE_FEE("征缴社会抚养费", "011002"),
    SOCIAL_INSURANCE_FEE("征缴社会保险费", "011003"),
    SEWAGE_TREATMENT_FEE("征缴污水处理费", "011004"),
    CIVIL_AIR_DEFENSE_FEE("征缴防空地下室易地建设费", "011005"),
    SOIL_CONSERVATION_FEE("征缴水土保持补偿费", "011006"),
    LAND_IDLE_FEE("征缴土地闲置费", "011007"),
    LAND_RECLAMATION_FEE("征缴土地复垦费", "011008"),
    CULTIVATED_LAND_FEE("征缴耕地开垦费", "011009");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_COLLECTION;

    AdminCollection(String description, String code) {
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