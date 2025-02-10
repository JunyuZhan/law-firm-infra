package com.lawfirm.staff.model.request.lawyer.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "修改合同请求")
public class ContractUpdateRequest {

    @Schema(description = "合同ID")
    @NotNull(message = "合同ID不能为空")
    private Long id;

    @Schema(description = "合同名称")
    @NotBlank(message = "合同名称不能为空")
    private String name;

    @Schema(description = "合同类型")
    @NotNull(message = "合同类型不能为空")
    private Integer type;

    @Schema(description = "合同金额")
    @NotNull(message = "合同金额不能为空")
    private BigDecimal amount;

    @Schema(description = "签订日期")
    @NotNull(message = "签订日期不能为空")
    private LocalDateTime signDate;

    @Schema(description = "生效日期")
    @NotNull(message = "生效日期不能为空")
    private LocalDateTime effectiveDate;

    @Schema(description = "到期日期")
    @NotNull(message = "到期日期不能为空")
    private LocalDateTime expiryDate;

    @Schema(description = "客户ID")
    @NotNull(message = "客户ID不能为空")
    private Long clientId;

    @Schema(description = "客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String clientName;

    @Schema(description = "律师ID")
    @NotNull(message = "律师ID不能为空")
    private Long lawyerId;

    @Schema(description = "律师姓名")
    @NotBlank(message = "律师姓名不能为空")
    private String lawyerName;

    @Schema(description = "分支机构ID")
    @NotNull(message = "分支机构ID不能为空")
    private Long branchId;

    @Schema(description = "部门ID")
    @NotNull(message = "部门ID不能为空")
    private Long departmentId;

    @Schema(description = "合同描述")
    private String description;

    @Schema(description = "备注")
    private String remark;
} 