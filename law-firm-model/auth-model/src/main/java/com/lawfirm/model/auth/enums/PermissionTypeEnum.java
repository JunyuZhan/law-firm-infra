package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 权限类型枚举
 */
@Getter
public enum PermissionTypeEnum implements BaseEnum<Integer> {
    
    /**
     * 菜单
     */
    MENU(0, "菜单"),
    
    /**
     * 按钮
     */
    BUTTON(1, "按钮"),
    
    /**
     * API接口
     */
    API(2, "API接口");
    
    private final Integer code;
    private final String desc;
    
    PermissionTypeEnum(Integer code, String desc) {
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