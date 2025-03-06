package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 操作类型枚举
 */
@Getter
public enum OperationTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 完全权限（增删改查）
     */
    FULL(0, "完全权限"),
    
    /**
     * 只读权限（仅查看）
     */
    READ_ONLY(1, "只读权限"),
    
    /**
     * 申请权限（需审批）
     */
    APPLY(2, "申请权限"),
    
    /**
     * 审批权限（可审批）
     */
    APPROVE(3, "审批权限"),
    
    /**
     * 团队权限（可操作团队数据）
     */
    TEAM(4, "团队权限"),
    
    /**
     * 个人权限（仅可操作个人数据）
     */
    PERSONAL(5, "个人权限");

    private final Integer code;
    private final String desc;
    
    OperationTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
} 