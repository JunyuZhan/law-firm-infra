package com.lawfirm.analysis.model.vo;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 统计分析记录VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisRecordVO extends BaseDTO {

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