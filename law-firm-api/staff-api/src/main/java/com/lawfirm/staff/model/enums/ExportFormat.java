package com.lawfirm.staff.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "导出格式枚举")
public enum ExportFormat {
    
    EXCEL("excel", "Excel格式"),
    PDF("pdf", "PDF格式");

    private final String code;
    private final String desc;

    ExportFormat(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 