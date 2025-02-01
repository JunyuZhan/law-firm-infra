package com.lawfirm.system.enums;

import lombok.Getter;

/**
 * 升级状态枚举
 */
@Getter
public enum UpgradeStatusEnum {
    
    PENDING(0, "待升级"),
    UPGRADING(1, "升级中"),
    SUCCESS(2, "升级成功"),
    FAILED(3, "升级失败");
    
    private final Integer code;
    private final String desc;
    
    UpgradeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static UpgradeStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UpgradeStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 