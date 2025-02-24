package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政复议三级案由
 */
@Getter
public enum AdminReconsideration implements BaseEnum<String> {
    REJECT_ACCEPTANCE("不予受理行政复议申请决定", "011601"),
    REJECT_APPLICATION("驳回行政复议申请决定", "011602"),
    ADMINISTRATIVE_ACTION("行政行为及行政复议", "011603"),
    CHANGE_DECISION("改变原行政行为的行政复议决定", "011604");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_RECONSIDERATION;

    AdminReconsideration(String description, String code) {
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