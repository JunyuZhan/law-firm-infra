package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政强制执行三级案由
 */
@Getter
public enum AdminEnforcement implements BaseEnum<String> {
    ADD_FINE("加处罚款或者滞纳金", "010301"),
    TRANSFER_DEPOSITS("划拨存款、汇款", "010302"),
    AUCTION_SEALED("拍卖查封、扣押的场所、设施或者财物", "010303"),
    HANDLE_SEALED("处理查封、扣押的场所、设施或者财物", "010304"),
    REMOVE_OBSTACLES("排除妨碍", "010305"),
    RESTORE_STATUS("恢复原状", "010306"),
    SUBSTITUTE_PERFORMANCE("代履行", "010307"),
    FORCED_DEMOLITION("强制拆除房屋或者设施", "010308"),
    FORCED_REMOVAL("强制清除地上物", "010309");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_ENFORCEMENT;

    AdminEnforcement(String description, String code) {
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