package com.lawfirm.model.finance.vo.transaction;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import com.lawfirm.model.finance.enums.PaymentMethodEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易详情VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TransactionDetailVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号
     */
    private String transactionNumber;

    /**
     * 交易类型
     */
    private TransactionTypeEnum transactionType;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 支付方式
     */
    private PaymentMethodEnum paymentMethod;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 交易状态（0-待处理，1-处理中，2-已完成，3-已取消，4-已退款）
     */
    private Integer transactionStatus;

    /**
     * 付款账户ID
     */
    private Long fromAccountId;

    /**
     * 付款账户名称
     */
    private String fromAccountName;

    /**
     * 收款账户ID
     */
    private Long toAccountId;

    /**
     * 收款账户名称
     */
    private String toAccountName;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联案件编号
     */
    private String caseNumber;

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
     * 交易说明
     */
    private String description;

    /**
     * 附件URL列表，JSON格式
     */
    private String attachments;

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