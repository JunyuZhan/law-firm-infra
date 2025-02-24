package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政补偿三级案由
 */
@Getter
public enum AdminCompensation implements BaseEnum<String> {
    HOUSE_REQUISITION("房屋征收或者征用补偿", "011901"),
    LAND_REQUISITION("土地征收或者征用补偿", "011902"),
    MOVABLE_PROPERTY("动产征收或者征用补偿", "011903"),
    LICENSE_WITHDRAWAL("撤回行政许可补偿", "011904"),
    LAND_USE_RIGHT("收回国有土地使用权补偿", "011905"),
    PLANNING_CHANGE("规划变更补偿", "011906"),
    RESETTLEMENT("移民安置补偿", "011907");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_COMPENSATION;

    AdminCompensation(String description, String code) {
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