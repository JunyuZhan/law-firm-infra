package com.lawfirm.model.finance.vo.receivable;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 应收账款详情VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReceivableDetailVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 应收账款编号
     */
    private String receivableNo;

    /**
     * 应收账款名称
     */
    private String receivableName;

    /**
     * 关联合同ID
     */
    private Long contractId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 应收总金额
     */
    private BigDecimal totalAmount;

    /**
     * 已收金额
     */
    private BigDecimal receivedAmount;

    /**
     * 未收金额
     */
    private BigDecimal unreceivedAmount;

    /**
     * 应收款币种
     */
    private CurrencyEnum currency;

    /**
     * 账期（天）
     */
    private Integer creditPeriod;

    /**
     * 预计收款日期
     */
    private LocalDateTime expectedReceiptDate;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 应收款状态
     */
    private Integer statusCode;

    /**
     * 关联付款计划ID
     */
    private Long paymentPlanId;

    /**
     * 关联案件ID
     */
    private Long caseId;

    /**
     * 关联客户ID
     */
    private Long clientId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 负责律师ID
     */
    private Long lawyerId;

    /**
     * 律师名称
     */
    private String lawyerName;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 最近收款日期
     */
    private LocalDateTime lastReceiptDate;

    /**
     * 应收款备注
     */
    private String remark;

    /**
     * 获取应收款状态枚举
     */
    public ReceivableStatusEnum getStatusEnum() {
        return ReceivableStatusEnum.getByCode(this.statusCode);
    }

    /**
     * 设置应收款状态枚举
     */
    public void setStatusEnum(ReceivableStatusEnum statusEnum) {
        this.statusCode = statusEnum != null ? statusEnum.getCode() : null;
    }
} 