package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政奖励三级案由
 */
@Getter
public enum AdminReward implements BaseEnum<String> {
    HONORARY_TITLE("授予荣誉称号", "011101"),
    BONUS_PAYMENT("发放奖金", "011102");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_REWARD;

    AdminReward(String description, String code) {
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