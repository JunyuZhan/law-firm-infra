package com.lawfirm.model.finance.vo.income;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收入列表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IncomeListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 收入编号
     */
    private String incomeNumber;

    /**
     * 收入类型
     */
    private IncomeTypeEnum incomeType;

    /**
     * 收入金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 收入时间
     */
    private LocalDateTime incomeTime;

    /**
     * 收款账户名称
     */
    private String accountName;

    /**
     * 关联案件编号
     */
    private String caseNumber;

    /**
     * 关联客户名称
     */
    private String clientName;

    /**
     * 关联律师名称
     */
    private String lawyerName;

    /**
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 确认状态（0-未确认，1-已确认）
     */
    private Integer confirmStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;
} 