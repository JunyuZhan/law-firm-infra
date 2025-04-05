package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 外部日历同步状态枚举
 */
@Getter
public enum SyncStatus {
    
    NEVER_SYNCED(0, "从未同步"),
    SYNCING(1, "同步中"),
    SYNC_SUCCESS(2, "同步成功"),
    SYNC_FAILED(3, "同步失败"),
    TOKEN_EXPIRED(4, "Token已过期");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    // 手动添加构造函数
    SyncStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static SyncStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (SyncStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 