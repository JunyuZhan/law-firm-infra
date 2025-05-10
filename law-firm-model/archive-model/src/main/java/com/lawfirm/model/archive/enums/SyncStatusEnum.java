package com.lawfirm.model.archive.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 同步状态枚举
 */
@Getter
@AllArgsConstructor
@Schema(description = "同步状态枚举")
public enum SyncStatusEnum {

    /**
     * 待同步
     */
    PENDING(0, "待同步"),

    /**
     * 同步中
     */
    SYNCING(1, "同步中"),

    /**
     * 同步成功
     */
    SUCCESS(2, "同步成功"),

    /**
     * 同步失败
     */
    FAILED(3, "同步失败"),

    /**
     * 忽略同步
     */
    IGNORED(9, "忽略同步");

    /**
     * 编码
     */
    @EnumValue
    @JsonValue
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据状态码获取枚举
     */
    public static SyncStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (SyncStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 