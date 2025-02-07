package com.lawfirm.staff.model.request.lawyer.cases;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "更新案件请求")
public class CaseUpdateRequest {

    @Schema(description = "案件ID")
    @NotNull(message = "案件ID不能为空")
    private Long id;

    @Schema(description = "案件标题")
    @NotBlank(message = "案件标题不能为空")
    private String title;

    @Schema(description = "案件类型")
    @NotNull(message = "案件类型不能为空")
    private Integer type;

    @Schema(description = "案件状态")
    @NotNull(message = "案件状态不能为空")
    private Integer status;

    @Schema(description = "案件金额")
    private BigDecimal amount;

    @Schema(description = "客户ID")
    @NotNull(message = "客户不能为空")
    private Long clientId;

    @Schema(description = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String clientName;

    @Schema(description = "主办律师ID")
    @NotNull(message = "主办律师不能为空")
    private Long lawyerId;

    @Schema(description = "主办律师姓名")
    @NotBlank(message = "主办律师姓名不能为空")
    private String lawyerName;

    @Schema(description = "协办律师ID列表")
    private Long[] assistantLawyerIds;

    @Schema(description = "协办律师姓名列表")
    private String[] assistantLawyerNames;

    @Schema(description = "案件描述")
    private String description;

    @Schema(description = "备注")
    private String remark;
} 