package com.lawfirm.analysis.constant;

public final class AnalysisBusinessConstants {
    private AnalysisBusinessConstants() { throw new IllegalStateException("常量类不应被实例化"); }
    public static final class Controller {
        public static final String API_PREFIX = "/api/v1/analysis";
        public static final String API_TASK_PREFIX = "/api/v1/analysis/tasks";
        public static final String API_TASK_HISTORY_PREFIX = "/api/v1/analysis/task-histories";
        // 可扩展其它API前缀
    }
} 