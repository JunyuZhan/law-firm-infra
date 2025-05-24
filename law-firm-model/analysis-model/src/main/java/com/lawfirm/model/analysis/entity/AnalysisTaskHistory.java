package com.lawfirm.model.analysis.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 分析任务历史实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分析任务历史实体")
public class AnalysisTaskHistory extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    @Schema(description = "分析任务ID")
    private Long taskId;
    @Schema(description = "分析类型")
    private String analysisType;
    @Schema(description = "执行状态")
    private String execStatus;
    @Schema(description = "执行结果摘要")
    private String resultSummary;
    @Schema(description = "执行开始时间")
    private LocalDateTime startTime;
    @Schema(description = "执行结束时间")
    private LocalDateTime endTime;
    @Schema(description = "错误信息")
    private String errorMsg;
} 