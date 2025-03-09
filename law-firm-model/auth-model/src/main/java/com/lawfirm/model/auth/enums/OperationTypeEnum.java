package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 操作类型枚举（通用操作类型，不包含业务特定操作）
 */
@Getter
public enum OperationTypeEnum implements BaseEnum<String> {
    
    /**
     * 完全操作权限
     */
    FULL("full", "完全权限"),
    
    /**
     * 审批权限
     */
    APPROVE("approve", "审批权限"),
    
    /**
     * 创建权限
     */
    CREATE("create", "创建权限"),
    
    /**
     * 只读权限
     */
    READ_ONLY("read_only", "只读权限"),
    
    /**
     * 个人数据权限
     */
    PERSONAL("personal", "个人数据权限"),
    
    /**
     * 申请权限
     */
    APPLY("apply", "申请权限");
    
    private final String code;
    private final String name;
    
    OperationTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    @Override
    public String getValue() {
        return code;
    }
    
    @Override
    public String getDescription() {
        return name;
    }
    
    /**
     * 根据code获取枚举值
     *
     * @param code 编码
     * @return 枚举值
     */
    public static OperationTypeEnum getByCode(String code) {
        for (OperationTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 判断当前操作类型是否有权限执行指定的操作类型
     *
     * @param requiredType 需要的操作类型
     * @return 是否有权限
     */
    public boolean hasPermission(OperationTypeEnum requiredType) {
        // 操作类型的权限级别，从高到低
        // FULL > APPROVE > CREATE > READ_ONLY > PERSONAL > APPLY
        // 序号越小，权限越高
        int currentOrdinal = this.ordinal();
        int requiredOrdinal = requiredType.ordinal();
        
        // 当前权限级别小于等于需要的权限级别，则有权限
        return currentOrdinal <= requiredOrdinal;
    }
} 