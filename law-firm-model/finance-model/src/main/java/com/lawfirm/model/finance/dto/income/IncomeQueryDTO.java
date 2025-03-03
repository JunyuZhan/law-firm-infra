package com.lawfirm.model.finance.dto.income;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收入查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IncomeQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 收入编号
     */
    private String incomeNumber;

    /**
     * 收入类型
     */
    private IncomeTypeEnum incomeType;

    /**
     * 最小金额
     */
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    private BigDecimal maxAmount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 收入时间开始
     */
    private LocalDateTime incomeTimeStart;

    /**
     * 收入时间结束
     */
    private LocalDateTime incomeTimeEnd;

    /**
     * 收款账户ID
     */
    private Long accountId;

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
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 确认状态（0-未确认，1-已确认）
     */
    private Integer confirmStatus;

    /**
     * 创建时间开始
     */
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 