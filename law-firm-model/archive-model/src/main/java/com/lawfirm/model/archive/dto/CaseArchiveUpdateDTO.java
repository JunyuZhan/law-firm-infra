package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 案件档案更新DTO
 */
@Data
@Schema(description = "案件档案更新DTO")
public class CaseArchiveUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID
     */
    @NotBlank(message = "档案ID不能为空")
    @Schema(description = "档案ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    /**
     * 案件标题
     */
    @Schema(description = "案件标题")
    private String caseTitle;

    /**
     * 负责律师ID
     */
    @Schema(description = "负责律师ID")
    private String lawyerId;

    /**
     * 负责律师姓名
     */
    @Schema(description = "负责律师姓名")
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