package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件档案创建DTO
 */
@Data
@Schema(description = "案件档案创建DTO")
public class CaseArchiveCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @NotBlank(message = "案件ID不能为空")
    @Schema(description = "案件ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String caseId;

    /**
     * 案件编号
     */
    @NotBlank(message = "案件编号不能为空")
    @Schema(description = "案件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String caseNo;

    /**
     * 案件标题
     */
    @NotBlank(message = "案件标题不能为空")
    @Schema(description = "案件标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String caseTitle;

    /**
     * 负责律师ID
     */
    @NotBlank(message = "负责律师ID不能为空")
    @Schema(description = "负责律师ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lawyerId;

    /**
     * 负责律师姓名
     */
    @NotBlank(message = "负责律师姓名不能为空")
    @Schema(description = "负责律师姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lawyerName;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String clientId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String clientName;

    /**
     * 案件类型
     */
    @Schema(description = "案件类型")
    private String caseType;

    /**
     * 案件状态
     */
    @Schema(description = "案件状态")
    private String caseStatus;

    /**
     * 案件金额
     */
    @Schema(description = "案件金额")
    private BigDecimal caseAmount;

    /**
     * 案件开始时间
     */
    @Schema(description = "案件开始时间")
    private LocalDateTime caseStartTime;

    /**
     * 案件结束时间
     */
    @Schema(description = "案件结束时间")
    private LocalDateTime caseEndTime;

    /**
     * 归档备注
     */
    @Schema(description = "归档备注")
    private String archiveRemark;
} 