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
@Table(name = "case_evaluation")
@EqualsAndHashCode(callSuper = true)
public class CaseEvaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("案件ID")
    @Column(nullable = false)
    private Long caseId;

    @Comment("评估类型(INITIAL-初步评估/RISK-风险评估/COST-成本评估/BENEFIT-收益评估)")
    @Column(nullable = false, length = 32)
    private String evaluationType;

    @Comment("评估人")
    @Column(nullable = false, length = 64)
    private String evaluator;

    @Comment("评估时间")
    @Column(nullable = false)
    private LocalDateTime evaluationTime;

    @Comment("案情复杂度(1-5)")
    @Column(nullable = false)
    private Integer complexity;

    @Comment("胜诉可能性(%)")
    @Column(nullable = false)
    private Integer winningProbability;

    @Comment("预计工作量(小时)")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedWorkload;

    @Comment("预计收费")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal estimatedFee;

    @Comment("预计成本")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal estimatedCost;

    @Comment("预计利润")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal estimatedProfit;

    @Comment("风险等级(LOW-低风险/MEDIUM-中风险/HIGH-高风险)")
    @Column(nullable = false, length = 32)
    private String riskLevel;

    @Comment("主要风险点")
    @Column(length = 512)
    private String riskPoints;

    @Comment("风险应对措施")
    @Column(length = 512)
    private String riskMeasures;

    @Comment("评估结论")
    @Column(nullable = false, length = 512)
    private String conclusion;

    @Comment("评估建议")
    @Column(length = 512)
    private String suggestions;

    @Comment("备注")
    @Column(length = 512)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case lawCase;
} 