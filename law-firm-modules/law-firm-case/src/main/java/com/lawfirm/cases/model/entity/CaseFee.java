package com.lawfirm.cases.model.entity;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "case_fee")
@EqualsAndHashCode(callSuper = true)
public class CaseFee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("费用类型(RETAINER-预收费用/SERVICE-服务费用/COURT-诉讼费用/OTHER-其他费用)")
    @Column(nullable = false, length = 32)
    private String feeType;

    @Comment("费用名称")
    @Column(nullable = false, length = 128)
    private String feeName;

    @Comment("费用金额")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Comment("币种")
    @Column(nullable = false, length = 16)
    private String currency;

    @Comment("收付类型(INCOME-收入/EXPENSE-支出)")
    @Column(nullable = false, length = 32)
    private String transactionType;

    @Comment("计划收付时间")
    @Column(nullable = false)
    private LocalDateTime plannedTime;

    @Comment("实际收付时间")
    private LocalDateTime actualTime;

    @Comment("收付状态(PENDING-待收付/PARTIAL-部分收付/COMPLETED-已完成)")
    @Column(nullable = false, length = 32)
    private String transactionStatus;

    @Comment("已收付金额")
    @Column(precision = 19, scale = 2)
    private BigDecimal paidAmount;

    @Comment("收付方式")
    @Column(length = 32)
    private String paymentMethod;

    @Comment("收付账号")
    @Column(length = 64)
    private String accountNo;

    @Comment("发票号")
    @Column(length = 32)
    private String invoiceNo;

    @Comment("开票时间")
    private LocalDateTime invoiceTime;

    @Comment("费用说明")
    @Column(length = 512)
    private String description;

    @Comment("备注")
    @Column(length = 512)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 