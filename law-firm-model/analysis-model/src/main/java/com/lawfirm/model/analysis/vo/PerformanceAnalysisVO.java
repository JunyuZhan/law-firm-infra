package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.personnel.vo.PersonVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绩效分析VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "绩效分析结果VO")
public class PerformanceAnalysisVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    /** 人员信息 */
    @Schema(description = "人员信息")
    private PersonVO person;
    /** 绩效得分 */
    @Schema(description = "绩效得分")
    private Double score;
    /** 完成任务数 */
    @Schema(description = "完成任务数")
    private Integer taskCount;
    /** 工时 */
    @Schema(description = "工时")
    private Double workHours;
    /** 排名 */
    @Schema(description = "排名")
    private Integer rank;
} 