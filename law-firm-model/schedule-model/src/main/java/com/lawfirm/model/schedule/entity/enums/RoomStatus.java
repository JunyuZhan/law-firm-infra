package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 会议室状态枚举
 */
@Getter
public enum RoomStatus {
    
    AVAILABLE(0, "可用"),
    MAINTENANCE(1, "维护中"),
    DISABLED(2, "停用");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    RoomStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static RoomStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (RoomStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 