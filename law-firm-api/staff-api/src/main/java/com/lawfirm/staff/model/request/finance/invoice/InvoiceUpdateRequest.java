package com.lawfirm.staff.model.request.finance.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "发票更新请求")
public class InvoiceUpdateRequest {
    
    @Schema(description = "发票ID")
    @NotNull(message = "发票ID不能为空")
    private Long id;
    
    @Schema(description = "发票抬头")
    private String title;
    
    @Schema(description = "发票类型")
    private Integer type;
    
    @Schema(description = "发票金额")
    private BigDecimal amount;
    
    @Schema(description = "税号")
    private String taxNumber;
    
    @Schema(description = "开户行")
    private String bankName;
    
    @Schema(description = "银行账号")
    private String bankAccount;
    
    @Schema(description = "联系电话")
    private String phone;
    
    @Schema(description = "邮寄地址")
    private String address;
    
    @Schema(description = "关联案件ID")
    private Long caseId;
    
    @Schema(description = "关联客户ID")
    private Long clientId;
    
    @Schema(description = "备注")
    private String remark;
} 