package com.lawfirm.model.analysis.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 分析任务实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisTask extends ModelBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 分析类型 */
    @Schema(description = "分析类型")
    private String analysisType;
    /** 任务状态 */
    @Schema(description = "任务状态")
    private String taskStatus;
    /** 任务描述 */
    @Schema(description = "任务描述")
    private String description;
} 