package com.lawfirm.analysis.model.dto;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 统计分析请求DTO
 */
@Data
public class AnalysisRequest {

    /**
     * 分析类型
     */
    @NotNull(message = "分析类型不能为空")
    private AnalysisTypeEnum analysisType;

    /**
     * 分析开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 分析结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
} 