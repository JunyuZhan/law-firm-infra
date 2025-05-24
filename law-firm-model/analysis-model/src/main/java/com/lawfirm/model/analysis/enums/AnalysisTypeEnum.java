package com.lawfirm.model.analysis.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 分析类型枚举
 */
@Schema(description = "分析类型枚举")
public enum AnalysisTypeEnum {
    CASE, FINANCE, PERFORMANCE, CLIENT, CONTRACT, TREND
} 