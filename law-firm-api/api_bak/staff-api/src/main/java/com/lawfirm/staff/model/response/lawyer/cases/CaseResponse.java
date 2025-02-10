package com.lawfirm.staff.model.response.lawyer.cases;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "案件响应")
public class CaseResponse {

    @Schema(description = "案件ID")
    private Long id;

    @Schema(description = "案件编号")
    private String caseNo;

    @Schema(description = "案件标题")
    private String title;

    @Schema(description = "案件类型")
    private Integer type;

    @Schema(description = "案件状态")
    private Integer status;

    @Schema(description = "案件金额")
    private BigDecimal amount;

    @Schema(description = "客户ID")
    private Long clientId;

    @Schema(description = "客户名称")
    private String clientName;

    @Schema(description = "主办律师ID")
    private Long lawyerId;

    @Schema(description = "主办律师姓名")
    private String lawyerName;

    @Schema(description = "协办律师ID列表")
    private Long[] assistantLawyerIds;

    @Schema(description = "协办律师姓名列表")
    private String[] assistantLawyerNames;

    @Schema(description = "案件描述")
    private String description;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updateBy;
} 