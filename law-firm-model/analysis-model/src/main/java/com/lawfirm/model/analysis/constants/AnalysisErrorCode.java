package com.lawfirm.model.analysis.constants;

/**
 * 分析模块错误码常量
 */
public class AnalysisErrorCode {
    private AnalysisErrorCode() {}

    public static final String ANALYSIS_TASK_NOT_FOUND = "ANALYSIS_001";
    public static final String INVALID_ANALYSIS_TYPE = "ANALYSIS_002";
    public static final String DATA_SOURCE_ERROR = "ANALYSIS_003";
    public static final String PERMISSION_DENIED = "ANALYSIS_004";
    public static final String EXPORT_FAILED = "ANALYSIS_005";
    // ... 可根据实际业务继续扩展
} 