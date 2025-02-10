package com.lawfirm.analysis.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 统计分析记录实体
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("analysis_record")
public class AnalysisRecord extends BaseEntity {

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
} 