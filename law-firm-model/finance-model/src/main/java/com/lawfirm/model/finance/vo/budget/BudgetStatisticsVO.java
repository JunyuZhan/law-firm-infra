package com.lawfirm.model.finance.vo.budget;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.BudgetTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

/**
 * 预算统计VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BudgetStatisticsVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 总预算金额
     */
    private BigDecimal totalAmount;

    /**
     * 总已使用金额
     */
    private BigDecimal totalUsedAmount;

    /**
     * 总剩余金额
     */
    private BigDecimal totalRemainingAmount;

    /**
     * 预算使用率
     */
    private BigDecimal usageRate;

    /**
     * 按预算类型统计
     */
    private transient Map<BudgetTypeEnum, BudgetTypeStatistics> budgetTypeStatistics;

    /**
     * 按部门统计
     */
    private transient Map<String, DepartmentBudgetStatistics> departmentStatistics;

    /**
     * 按业务线统计
     */
    private transient Map<String, BusinessLineBudgetStatistics> businessLineStatistics;

    /**
     * 按预算项目统计
     */
    private transient Map<String, BudgetItemStatistics> budgetItemStatistics;

    /**
     * 按月份统计
     */
    private transient Map<String, MonthlyBudgetStatistics> monthlyStatistics;

    /**
     * 按成本中心统计
     */
    private transient List<CostCenterBudgetStatistics> costCenterStatistics;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, BigDecimal> currencyStatistics;

    /**
     * 预算类型统计
     */
    @Data
    public static class BudgetTypeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 预算类型
         */
        private BudgetTypeEnum budgetType;

        /**
         * 预算金额
         */
        private BigDecimal amount;

        /**
         * 已使用金额
         */
        private BigDecimal usedAmount;

        /**
         * 剩余金额
         */
        private BigDecimal remainingAmount;

        /**
         * 使用率
         */
        private BigDecimal usageRate;
    }

    /**
     * 部门预算统计
     */
    @Data
    public static class DepartmentBudgetStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 部门ID
         */
        private Long departmentId;

        /**
         * 部门名称
         */
        private String departmentName;

        /**
         * 预算金额
         */
        private BigDecimal amount;

        /**
         * 已使用金额
         */
        private BigDecimal usedAmount;

        /**
         * 剩余金额
         */
        private BigDecimal remainingAmount;

        /**
         * 使用率
         */
        private BigDecimal usageRate;
    }

    /**
     * 业务线预算统计
     */
    @Data
    public static class BusinessLineBudgetStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 业务线ID
         */
        private Long businessLineId;

        /**
         * 业务线名称
         */
        private String businessLineName;

        /**
         * 预算金额
         */
        private BigDecimal amount;

        /**
         * 已使用金额
         */
        private BigDecimal usedAmount;

        /**
         * 剩余金额
         */
        private BigDecimal remainingAmount;

        /**
         * 使用率
         */
        private BigDecimal usageRate;
    }

    /**
     * 预算项目统计
     */
    @Data
    public static class BudgetItemStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 预算项目ID
         */
        private Long budgetItemId;

        /**
         * 预算项目名称
         */
        private String budgetItemName;

        /**
         * 预算金额
         */
        private BigDecimal amount;

        /**
         * 已使用金额
         */
        private BigDecimal usedAmount;

        /**
         * 剩余金额
         */
        private BigDecimal remainingAmount;

        /**
         * 使用率
         */
        private BigDecimal usageRate;
    }

    /**
     * 月度预算统计
     */
    @Data
    public static class MonthlyBudgetStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 月份
         */
        private String month;

        /**
         * 预算金额
         */
        private BigDecimal amount;

        /**
         * 已使用金额
         */
        private BigDecimal usedAmount;

        /**
         * 剩余金额
         */
        private BigDecimal remainingAmount;

        /**
         * 使用率
         */
        private BigDecimal usageRate;
    }

    /**
     * 成本中心预算统计
     */
    @Data
    public static class CostCenterBudgetStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 成本中心ID
         */
        private Long costCenterId;

        /**
         * 成本中心名称
         */
        private String costCenterName;

        /**
         * 预算金额
         */
        private BigDecimal amount;

        /**
         * 已使用金额
         */
        private BigDecimal usedAmount;

        /**
         * 剩余金额
         */
        private BigDecimal remainingAmount;

        /**
         * 使用率
         */
        private BigDecimal usageRate;
    }
} 