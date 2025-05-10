package com.lawfirm.model.archive.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 档案状态枚举
 */
@Getter
@AllArgsConstructor
@Schema(description = "档案状态枚举")
public enum ArchiveStatusEnum {

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 借出
     */
    BORROWED(2, "借出"),

    /**
     * 遗失
     */
    LOST(3, "遗失"),

    /**
     * 损坏
     */
    DAMAGED(4, "损坏"),

    /**
     * 已销毁
     */
    DESTROYED(5, "已销毁");

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
    public static ArchiveStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ArchiveStatusEnum status : values()) {
            if (status.getCode().equals(value)) {
                return status;
            }
        }
        return null;
    }
} 