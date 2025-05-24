package com.lawfirm.model.analysis.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 分析任务状态枚举
 */
@Schema(description = "分析任务状态枚举")
public enum AnalysisStatusEnum {
    INIT, // 初始化
    RUNNING, // 分析中
    SUCCESS, // 成功
    FAILED // 失败
} 