package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.StatusEnum;
import lombok.Getter;

@Getter
public enum CaseStatusEnum {
    DRAFT(1, "草稿"),
    PENDING(2, "待处理"),
    IN_PROGRESS(3, "处理中"),
    SUSPENDED(4, "已暂停"),
    COMPLETED(5, "已完成"),
    CLOSED(6, "已结案"),
    ARCHIVED(7, "已归档"),
    CANCELLED(8, "已取消"),
    REOPENED(9, "已重启");

    private final Integer code;
    private final String description;

    CaseStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CaseStatusEnum getByCode(Integer code) {
        for (CaseStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public static CaseStatusEnum getByCode(StatusEnum status) {
        return getByCode(Integer.parseInt(status.getValue()));
    }
} 