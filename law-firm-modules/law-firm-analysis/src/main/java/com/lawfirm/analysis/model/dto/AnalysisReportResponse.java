package com.lawfirm.analysis.model.dto;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 统计分析报告响应DTO
 */
@Data
public class AnalysisReportResponse {

    /**
     * 报告ID
     */
    private Long id;

    /**
     * 分析类型
     */
    private AnalysisTypeEnum analysisType;

    /**
     * 报告标题
     */
    private String title;

    /**
     * 报告描述
     */
    private String description;

    /**
     * 报告开始时间
     */
    private LocalDateTime startTime;

    /**
     * 报告结束时间
     */
    private LocalDateTime endTime;

    /**
     * 报告内容JSON
     */
    private String contentJson;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 