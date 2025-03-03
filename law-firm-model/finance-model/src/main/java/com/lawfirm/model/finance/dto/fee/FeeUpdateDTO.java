package com.lawfirm.model.finance.dto.fee;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FeeUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "费用ID不能为空")
    private Long id;

    /**
     * 费用类型
     */
    private FeeTypeEnum feeType;

    /**
     * 费用名称
     */
    @Size(max = 100, message = "费用名称长度不能超过100个字符")
    private String feeName;

    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 费用发生时间
     */
    private LocalDateTime feeTime;

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
     * 费用说明
     */
    @Size(max = 500, message = "费用说明长度不能超过500个字符")
    private String description;
} 