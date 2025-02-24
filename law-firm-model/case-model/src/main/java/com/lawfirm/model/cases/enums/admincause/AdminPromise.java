package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政允诺三级案由
 */
@Getter
public enum AdminPromise implements BaseEnum<String> {
    FULFILL_BONUS("兑现奖金", "010901"),
    FULFILL_PREFERENCE("兑现优惠", "010902");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_PROMISE;

    AdminPromise(String description, String code) {
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