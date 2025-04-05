package com.lawfirm.model.schedule.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 响应状态枚举
 */
@Getter
public enum ResponseStatus {
    
    PENDING(0, "未回复"),
    ACCEPTED(1, "已接受"),
    DECLINED(2, "已拒绝"),
    TENTATIVE(3, "暂定");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
    
    ResponseStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static ResponseStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResponseStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 