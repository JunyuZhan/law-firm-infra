package com.lawfirm.analysis.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 统计分析报告实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("analysis_report")
public class AnalysisReport extends BaseEntity {

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
