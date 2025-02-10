package com.lawfirm.staff.model.response.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用响应
 */
@Data
@Schema(description = "费用响应")
public class ExpenseResponse {

    @Schema(description = "费用ID")
    private Long id;

    @Schema(description = "费用类型ID")
    private Long categoryId;

    @Schema(description = "费用类型名称")
    private String categoryName;

    @Schema(description = "费用金额")
    private BigDecimal amount;

    @Schema(description = "费用发生时间")
    private LocalDateTime expenseTime;

    @Schema(description = "费用说明")
    private String description;

    @Schema(description = "关联发票")
    private List<InvoiceInfo> invoices;

    @Schema(description = "关联附件")
    private List<String> attachments;

    @Schema(description = "预算项目ID")
    private Long budgetItemId;

    @Schema(description = "预算项目名称")
    private String budgetItemName;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联案件名称")
    private String caseName;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "关联客户名称")
    private String clientName;

    @Schema(description = "状态(0:草稿 1:待审核 2:已审核 3:已驳回)")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "申请人ID")
    private Long applicantId;

    @Schema(description = "申请人姓名")
    private String applicantName;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审核人")
    private String approver;

    @Schema(description = "审核时间")
    private LocalDateTime approveTime;

    @Schema(description = "审核意见")
    private String approveRemark;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Data
    @Schema(description = "发票信息")
    public static class InvoiceInfo {
        
        @Schema(description = "发票ID")
        private Long id;
        
        @Schema(description = "发票号码")
        private String invoiceNo;
        
        @Schema(description = "发票金额")
        private BigDecimal amount;
        
        @Schema(description = "开票日期")
        private LocalDateTime invoiceDate;
    }
} 