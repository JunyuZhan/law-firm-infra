package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政协议三级案由
 */
@Getter
public enum AdminAgreement implements BaseEnum<String> {
    CONCLUDE_AGREEMENT("订立行政协议", "011801"),
    UNILATERAL_CHANGE("单方变更行政协议", "011802"),
    UNILATERAL_TERMINATION("单方解除行政协议", "011803"),
    ILLEGAL_PERFORMANCE("不依法履行行政协议", "011804"),
    BREACH_AGREEMENT("未按约定履行行政协议", "011805"),
    AGREEMENT_COMPENSATION("行政协议行政补偿", "011806"),
    AGREEMENT_INDEMNITY("行政协议行政赔偿", "011807"),
    REVOKE_AGREEMENT("撤销行政协议", "011808"),
    TERMINATE_AGREEMENT("解除行政协议", "011809"),
    CONTINUE_PERFORMANCE("继续履行行政协议", "011810"),
    CONFIRM_VALIDITY("确认行政协议无效或有效", "011811");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_AGREEMENT;

    AdminAgreement(String description, String code) {
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