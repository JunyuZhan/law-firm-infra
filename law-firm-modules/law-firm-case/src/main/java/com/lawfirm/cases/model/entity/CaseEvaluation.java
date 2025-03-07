package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件评估实体
 */
@Data
@TableName("case_evaluation")
@EqualsAndHashCode(callSuper = true)
public class CaseEvaluation extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 评估类型(INITIAL-初步评估/RISK-风险评估/COST-成本评估/BENEFIT-收益评估)
     */
    @TableField("evaluation_type")
    private String evaluationType;

    /**
     * 评估人
     */
    @TableField("evaluator")
    private String evaluator;

    /**
     * 评估时间
     */
    @TableField("evaluation_time")
    private LocalDateTime evaluationTime;

    /**
     * 案情复杂度(1-5)
     */
    @TableField("complexity")
    private Integer complexity;

    /**
     * R胜诉可能性(%)
     */
    @TableField("winning_probability")
    private Integer winningProbability;

    /**
     * 预计工作量(小时)
     */
    @TableField("estimated_workload")
    private BigDecimal estimatedWorkload;

    /**
     * 预计收费
     */
    @TableField("estimated_fee")
    private BigDecimal estimatedFee;

    /**
     * 预计成本
     */
    @TableField("estimated_cost")
    private BigDecimal estimatedCost;

    /**
     * 预计利润
     */
    @TableField("estimated_profit")
    private BigDecimal estimatedProfit;

    /**
     * 风险等级(LOW-低风险/MEDIUM-中风险/HIGH-高风险)
     */
    @TableField("risk_level")
    private String riskLevel;

    /**
     * 主要风险点
     */
    @TableField("risk_points")
    private String riskPoints;

    /**
     * 风险应对措施
     */
    @TableField("risk_measures")
    private String riskMeasures;

    /**
     * 评估结论
     */
    @TableField("conclusion")
    private String conclusion;

    /**
     * 评估建议
     */
    @TableField("suggestions")
    private String suggestions;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 关联的案件对象
     */
    @TableField(exist = false)
    private Case lawCase;
} 