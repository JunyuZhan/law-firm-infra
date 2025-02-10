package com.lawfirm.analysis.model.query;

import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.common.data.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 统计分析报告查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisReportQuery extends PageQuery {

    /**
     * 分析类型
     */
    private AnalysisTypeEnum analysisType;

    /**
     * 报告标题
     */
    private String title;

    /**
     * 开始时间范围（起）
     */
    private LocalDateTime startTimeBegin;

    /**
     * 开始时间范围（止）
     */
    private LocalDateTime startTimeEnd;

    /**
     * 结束时间范围（起）
     */
    private LocalDateTime endTimeBegin;

    /**
     * 结束时间范围（止）
     */
    private LocalDateTime endTimeEnd;
} 