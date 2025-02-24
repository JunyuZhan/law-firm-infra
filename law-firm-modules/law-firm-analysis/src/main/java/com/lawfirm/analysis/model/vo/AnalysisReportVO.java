package com.lawfirm.analysis.model.vo;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 统计分析报告VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisReportVO extends BaseDTO {

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
} 
