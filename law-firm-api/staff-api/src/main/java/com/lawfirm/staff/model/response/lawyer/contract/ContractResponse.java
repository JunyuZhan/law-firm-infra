package com.lawfirm.staff.model.response.lawyer.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "合同响应")
public class ContractResponse {

    @Schema(description = "合同ID")
    private Long id;

    @Schema(description = "合同编号")
    private String contractNo;

    @Schema(description = "合同名称")
    private String name;

    @Schema(description = "合同类型")
    private Integer type;

    @Schema(description = "合同金额")
    private BigDecimal amount;

    @Schema(description = "签订日期")
    private LocalDateTime signDate;

    @Schema(description = "生效日期")
    private LocalDateTime effectiveDate;

    @Schema(description = "到期日期")
    private LocalDateTime expiryDate;

    @Schema(description = "客户ID")
    private Long clientId;

    @Schema(description = "客户名称")
    private String clientName;

    @Schema(description = "律师ID")
    private Long lawyerId;

    @Schema(description = "律师姓名")
    private String lawyerName;

    @Schema(description = "分支机构ID")
    private Long branchId;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "合同状态")
    private Integer status;

    @Schema(description = "文件ID")
    private String fileId;

    @Schema(description = "合同描述")
    private String description;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "更新人")
    private String updateBy;
} 