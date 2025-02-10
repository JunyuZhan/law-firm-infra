package com.lawfirm.model.contract.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.contract.constants.ContractConstants;
import com.lawfirm.model.contract.enums.PaymentTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合同付款计划实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = ContractConstants.TableNames.CONTRACT_PAYMENT)
public class ContractPayment extends ModelBaseEntity<ContractPayment> {

    @NotNull(message = "合同ID不能为空")
    private Long contractId;  // 合同ID

    private Integer paymentNumber;  // 付款期数
    private String paymentStage;    // 付款阶段

    @NotNull(message = "付款类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentTypeEnum paymentType;  // 付款类型

    @NotNull(message = "付款金额不能为空")
    @Column(nullable = false, precision = ContractConstants.Amount.PRECISION, scale = ContractConstants.Amount.SCALE)
    private BigDecimal amount;  // 付款金额

    @Column(precision = ContractConstants.Amount.PRECISION, scale = ContractConstants.Amount.SCALE)
    private BigDecimal paidAmount;  // 已付金额

    private BigDecimal paymentRatio;  // 付款比例
    private String paymentCondition;   // 付款条件
    private LocalDateTime planPaymentTime;  // 计划付款时间
    private LocalDateTime actualPaymentTime;  // 实际付款时间

    private String paymentMethod;  // 付款方式
    private String paymentAccount; // 付款账号
    private String receivingAccount; // 收款账号
    private String bankName;        // 开户银行

    private Boolean isCompleted;    // 是否已完成
    private String paymentStatus;   // 付款状态
    private LocalDateTime completionTime;  // 完成时间

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.REMARK)
    private String remark;  // 备注
}

