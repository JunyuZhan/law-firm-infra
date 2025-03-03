package com.lawfirm.model.finance.dto.fee;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FeeQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 费用编号
     */
    private String feeNumber;

    /**
     * 费用类型
     */
    private FeeTypeEnum feeType;

    /**
     * 费用名称
     */
    private String feeName;

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
     * 费用发生时间起始
     */
    private LocalDateTime feeTimeStart;

    /**
     * 费用发生时间结束
     */
    private LocalDateTime feeTimeEnd;

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
     * 创建时间起始
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