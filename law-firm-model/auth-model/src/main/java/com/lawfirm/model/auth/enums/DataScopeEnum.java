package com.lawfirm.model.auth.enums;

import lombok.Getter;

/**
 * 数据权限范围枚举
 */
@Getter
public enum DataScopeEnum {
    
    ALL("all", "全所数据", "可访问全部数据"),
    TEAM("team", "团队数据", "仅可访问本团队数据"),
    PERSONAL("personal", "个人数据", "仅可访问个人数据");

    private final String code;
    private final String name;
    private final String description;

    DataScopeEnum(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
} 