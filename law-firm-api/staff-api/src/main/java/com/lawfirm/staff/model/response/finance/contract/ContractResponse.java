package com.lawfirm.staff.model.response.finance.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "合同响应")
public class ContractResponse {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "合同编号")
    private String contractNo;

    @Schema(description = "合同名称")
    private String contractName;

    @Schema(description = "客户ID")
    private Long clientId;

    @Schema(description = "客户名称")
    private String clientName;

    @Schema(description = "案件ID")
    private Long caseId;

    @Schema(description = "案件名称")
    private String caseName;

    @Schema(description = "合同金额")
    private Double amount;

    @Schema(description = "已收金额")
    private Double receivedAmount;

    @Schema(description = "未收金额")
    private Double unreceivedAmount;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "签订日期")
    private String signDate;

    @Schema(description = "生效日期")
    private String effectiveDate;

    @Schema(description = "到期日期")
    private String expiryDate;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
} 