package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 预测分析结果VO
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked", "all"})
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "预测分析结果VO")
public class ForecastResultVO extends BaseVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 预测目标（如案件量、收入等） */
    @Schema(description = "预测目标（如案件量、收入等）")
    private String target;
    /** 预测周期 */
    @Schema(description = "预测周期")
    private String period;
    /** 预测值 */
    @Schema(description = "预测值")
    private Double forecastValue;
    /** 置信区间下限 */
    private Double lowerBound;
    /** 置信区间上限 */
    private Double upperBound;
    /** 预测趋势数据 */
    private List<TrendAnalysisVO> forecastTrends;
} 