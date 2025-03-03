package com.lawfirm.model.finance.vo.report;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 损益报表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ProfitLossReportVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 报表期间（格式：yyyy-MM）
     */
    private String reportPeriod;

    /**
     * 报表生成时间
     */
    private LocalDateTime generateTime;

    /**
     * 营业收入
     */
    private BigDecimal operatingIncome;

    /**
     * 营业成本
     */
    private BigDecimal operatingCost;

    /**
     * 毛利润
     */
    private BigDecimal grossProfit;

    /**
     * 营业费用
     */
    private BigDecimal operatingExpenses;

    /**
     * 管理费用
     */
    private BigDecimal administrativeExpenses;

    /**
     * 财务费用
     */
    private BigDecimal financialExpenses;

    /**
     * 营业利润
     */
    private BigDecimal operatingProfit;

    /**
     * 营业外收入
     */
    private BigDecimal nonOperatingIncome;

    /**
     * 营业外支出
     */
    private BigDecimal nonOperatingExpenses;

    /**
     * 利润总额
     */
    private BigDecimal totalProfit;

    /**
     * 所得税费用
     */
    private BigDecimal incomeTaxExpense;

    /**
     * 净利润
     */
    private BigDecimal netProfit;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, CurrencyProfitLoss> currencyProfitLoss;

    /**
     * 按部门统计
     */
    private transient List<DepartmentProfitLoss> departmentProfitLoss;

    /**
     * 按业务线统计
     */
    private transient List<BusinessLineProfitLoss> businessLineProfitLoss;

    /**
     * 币种损益
     */
    @Data
    public static class CurrencyProfitLoss implements Serializable {
        private static final long serialVersionUID = 1L;
        
        /**
         * 币种
         */
        private CurrencyEnum currency;

        /**
         * 营业收入
         */
        private BigDecimal operatingIncome;

        /**
         * 营业成本
         */
        private BigDecimal operatingCost;

        /**
         * 毛利润
         */
        private BigDecimal grossProfit;

        /**
         * 营业费用
         */
        private BigDecimal operatingExpenses;

        /**
         * 净利润
         */
        private BigDecimal netProfit;
    }

    /**
     * 部门损益
     */
    @Data
    public static class DepartmentProfitLoss implements Serializable {
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
         * 营业收入
         */
        private BigDecimal operatingIncome;

        /**
         * 营业成本
         */
        private BigDecimal operatingCost;

        /**
         * 毛利润
         */
        private BigDecimal grossProfit;

        /**
         * 营业费用
         */
        private BigDecimal operatingExpenses;

        /**
         * 净利润
         */
        private BigDecimal netProfit;

        /**
         * 收入占比
         */
        private BigDecimal incomePercentage;

        /**
         * 利润占比
         */
        private BigDecimal profitPercentage;
    }

    /**
     * 业务线损益
     */
    @Data
    public static class BusinessLineProfitLoss implements Serializable {
        private static final long serialVersionUID = 1L;
        
        /**
         * 业务线编码
         */
        private String businessLineCode;

        /**
         * 业务线名称
         */
        private String businessLineName;

        /**
         * 营业收入
         */
        private BigDecimal operatingIncome;

        /**
         * 营业成本
         */
        private BigDecimal operatingCost;

        /**
         * 毛利润
         */
        private BigDecimal grossProfit;

        /**
         * 营业费用
         */
        private BigDecimal operatingExpenses;

        /**
         * 净利润
         */
        private BigDecimal netProfit;

        /**
         * 收入占比
         */
        private BigDecimal incomePercentage;

        /**
         * 利润占比
         */
        private BigDecimal profitPercentage;
    }
} 