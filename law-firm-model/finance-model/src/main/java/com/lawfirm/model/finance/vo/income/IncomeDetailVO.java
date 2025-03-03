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
 * 收入详情VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IncomeDetailVO extends BaseVO {

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
     * 收款账户ID
     */
    private Long accountId;

    /**
     * 收款账户名称
     */
    private String accountName;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联案件编号
     */
    private String caseNumber;

    /**
     * 关联案件名称
     */
    private String caseName;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 关联客户名称
     */
    private String clientName;

    /**
     * 关联律师ID
     */
    private Long lawyerId;

    /**
     * 关联律师名称
     */
    private String lawyerName;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 关联部门名称
     */
    private String departmentName;

    /**
     * 关联成本中心ID
     */
    private Long costCenterId;

    /**
     * 关联成本中心名称
     */
    private String costCenterName;

    /**
     * 收入说明
     */
    private String description;

    /**
     * 附件URL列表，JSON格式
     */
    private String attachments;

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

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;
} 