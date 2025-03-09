package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 数据范围枚举（通用数据范围，不包含业务特定数据范围）
 */
@Getter
public enum DataScopeEnum implements BaseEnum<String> {
    
    /**
     * 全部数据权限
     */
    ALL("all", "全部数据"),
    
    /**
     * 部门全权数据权限
     */
    DEPARTMENT_FULL("department_full", "部门全权数据"),
    
    /**
     * 团队数据权限
     */
    TEAM("team", "团队数据"),
    
    /**
     * 部门相关数据权限
     */
    DEPARTMENT_RELATED("department_related", "部门相关数据"),
    
    /**
     * 仅个人数据权限
     */
    PERSONAL("personal", "个人数据"),
    
    /**
     * 自定义数据权限
     */
    CUSTOM("custom", "自定义数据");
    
    private final String code;
    private final String name;
    
    DataScopeEnum(String code, String name) {
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
    public static DataScopeEnum getByCode(String code) {
        for (DataScopeEnum scope : values()) {
            if (scope.getCode().equals(code)) {
                return scope;
            }
        }
        return null;
    }
    
    /**
     * 根据整数值获取枚举值
     * 
     * @param value 整数值
     * @return 枚举值
     */
    public static DataScopeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        
        // 根据值的索引返回对应的枚举值
        // 0-ALL, 1-DEPARTMENT_FULL, 2-TEAM, 3-DEPARTMENT_RELATED, 4-PERSONAL, 5-CUSTOM
        DataScopeEnum[] scopes = values();
        if (value >= 0 && value < scopes.length) {
            return scopes[value];
        }
        
        return null;
    }
    
    /**
     * 判断当前数据范围是否包含指定的数据范围
     *
     * @param requiredScope 需要的数据范围
     * @return 是否包含
     */
    public boolean contains(DataScopeEnum requiredScope) {
        // 数据范围的包含关系，从大到小
        // ALL > DEPARTMENT_FULL > TEAM > DEPARTMENT_RELATED > PERSONAL
        // 序号越小，范围越大
        int currentOrdinal = this.ordinal();
        int requiredOrdinal = requiredScope.ordinal();
        
        // 如果是CUSTOM，需要特殊处理
        if (this == CUSTOM || requiredScope == CUSTOM) {
            return false; // 自定义数据范围需要单独判断
        }
        
        // 当前数据范围小于等于需要的数据范围，则包含
        return currentOrdinal <= requiredOrdinal;
    }
} 