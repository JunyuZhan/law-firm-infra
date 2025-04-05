package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 预约状态枚举
 */
@Getter
public enum ReservationStatus {
    
    PENDING(0, "待审核"),
    CONFIRMED(1, "已确认"),
    CANCELED(2, "已取消"),
    REJECTED(3, "已拒绝");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ReservationStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ReservationStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ReservationStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 