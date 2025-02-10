package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文档权限类型枚举
 */
@Getter
public enum DocumentPermissionEnum {
    
    READ("READ", "读取"),
    WRITE("WRITE", "写入"),
    DELETE("DELETE", "删除"),
    SHARE("SHARE", "分享"),
    PRINT("PRINT", "打印"),
    DOWNLOAD("DOWNLOAD", "下载");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    DocumentPermissionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
} 