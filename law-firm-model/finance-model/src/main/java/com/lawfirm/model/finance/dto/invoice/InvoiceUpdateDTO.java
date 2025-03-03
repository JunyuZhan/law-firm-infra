package com.lawfirm.model.finance.dto.invoice;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.InvoiceTypeEnum;
import com.lawfirm.model.finance.enums.InvoiceStatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发票更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class InvoiceUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "发票ID不能为空")
    private Long id;

    /**
     * 发票类型
     */
    private InvoiceTypeEnum invoiceType;

    /**
     * 发票金额
     */
    private BigDecimal amount;

    /**
     * 发票抬头
     */
    @Size(max = 200, message = "发票抬头长度不能超过200个字符")
    private String title;

    /**
     * 纳税人识别号
     */
    @Size(max = 32, message = "纳税人识别号长度不能超过32个字符")
    private String taxpayerNumber;

    /**
     * 开票时间
     */
    private LocalDateTime invoiceTime;

    /**
     * 发票状态
     */
    private InvoiceStatusEnum invoiceStatus;

    /**
     * 关联账单ID
     */
    private Long billingId;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 关联律师ID
     */
    private Long lawyerId;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 发票备注
     */
    @Size(max = 500, message = "发票备注长度不能超过500个字符")
    private String remark;
} 