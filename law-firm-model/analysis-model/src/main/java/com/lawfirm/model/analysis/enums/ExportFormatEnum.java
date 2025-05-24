package com.lawfirm.model.analysis.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 报表导出格式枚举
 */
@Schema(description = "报表导出格式枚举")
public enum ExportFormatEnum {
    EXCEL, PDF, CSV
} 