package com.lawfirm.analysis.model.dto;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 统计分析响应DTO
 */
@Data
public class AnalysisResponse {

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 分析类型
     */
    private AnalysisTypeEnum analysisType;

    /**
     * 分析开始时间
     */
    private LocalDateTime startTime;

    /**
     * 分析结束时间
     */
    private LocalDateTime endTime;

    /**
     * 分析结果JSON
     */
    private String resultJson;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 