package com.lawfirm.analysis.model.dto;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 统计分析报告请求DTO
 */
@Data
public class AnalysisReportRequest {

    /**
     * 分析类型
     */
    @NotNull(message = "分析类型不能为空")
    private AnalysisTypeEnum analysisType;

    /**
     * 报告开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 报告结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    /**
     * 报告标题
     */
    private String title;

    /**
     * 报告描述
     */
    private String description;
} 