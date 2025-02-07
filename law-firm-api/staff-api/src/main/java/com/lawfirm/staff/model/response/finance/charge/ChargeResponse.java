package com.lawfirm.staff.model.response.finance.charge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "费用响应")
public class ChargeResponse {

    @Schema(description = "费用ID")
    private Long id;

    @Schema(description = "费用名称")
    private String name;

    @Schema(description = "费用类型")
    private Integer type;

    @Schema(description = "费用类型名称")
    private String typeName;

    @Schema(description = "费用金额")
    private BigDecimal amount;

    @Schema(description = "费用状态")
    private Integer status;

    @Schema(description = "费用状态名称")
    private String statusName;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联案件名称")
    private String caseName;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "关联客户名称")
    private String clientName;

    @Schema(description = "费用说明")
    private String description;

    @Schema(description = "审核人ID")
    private Long auditorId;

    @Schema(description = "审核人姓名")
    private String auditorName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核意见")
    private String auditComment;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "备注")
    private String remark;
} 