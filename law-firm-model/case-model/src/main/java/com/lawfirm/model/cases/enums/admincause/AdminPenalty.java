package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政处罚三级案由
 */
@Getter
public enum AdminPenalty implements BaseEnum<String> {
    WARNING("警告", "010101"),
    PUBLIC_CRITICISM("通报批评", "010102"),
    FINE("罚款", "010103"),
    CONFISCATE_ILLEGAL_INCOME("没收违法所得", "010104"),
    CONFISCATE_ILLEGAL_PROPERTY("没收非法财物", "010105"),
    SUSPEND_LICENSE("暂扣许可证件", "010106"),
    REVOKE_LICENSE("吊销许可证件", "010107"),
    LOWER_QUALIFICATION("降低资质等级", "010108"),
    ORDER_CLOSURE("责令关闭", "010109"),
    ORDER_SUSPENSION("责令停产停业", "010110"),
    RESTRICT_PRODUCTION("限制开展生产经营活动", "010111"),
    RESTRICT_PRACTICE("限制从业", "010112"),
    ADMINISTRATIVE_DETENTION("行政拘留", "010113"),
    NO_LICENSE_APPLICATION("不得申请行政许可", "010114"),
    ORDER_DEMOLITION("责令限期拆除", "010115");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_PENALTY;

    AdminPenalty(String description, String code) {
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