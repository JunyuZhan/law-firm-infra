package com.lawfirm.model.finance.vo.report;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

/**
 * 资产负债表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BalanceSheetVO extends BaseVO {

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
     * 流动资产
     */
    private CurrentAssets currentAssets;

    /**
     * 非流动资产
     */
    private NonCurrentAssets nonCurrentAssets;

    /**
     * 资产总计
     */
    private BigDecimal totalAssets;

    /**
     * 流动负债
     */
    private CurrentLiabilities currentLiabilities;

    /**
     * 非流动负债
     */
    private NonCurrentLiabilities nonCurrentLiabilities;

    /**
     * 负债总计
     */
    private BigDecimal totalLiabilities;

    /**
     * 所有者权益
     */
    private OwnersEquity ownersEquity;

    /**
     * 负债和所有者权益总计
     */
    private BigDecimal totalLiabilitiesAndEquity;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, CurrencyBalance> currencyBalances;

    /**
     * 按部门统计
     */
    private transient List<DepartmentBalance> departmentBalances;

    /**
     * 流动资产
     */
    @Data
    public static class CurrentAssets implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 货币资金
         */
        private BigDecimal cash;

        /**
         * 应收账款
         */
        private BigDecimal accountsReceivable;

        /**
         * 预付款项
         */
        private BigDecimal prepayments;

        /**
         * 其他应收款
         */
        private BigDecimal otherReceivables;

        /**
         * 流动资产合计
         */
        private BigDecimal totalCurrentAssets;
    }

    /**
     * 非流动资产
     */
    @Data
    public static class NonCurrentAssets implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 固定资产
         */
        private BigDecimal fixedAssets;

        /**
         * 无形资产
         */
        private BigDecimal intangibleAssets;

        /**
         * 长期待摊费用
         */
        private BigDecimal longTermDeferredExpenses;

        /**
         * 其他非流动资产
         */
        private BigDecimal otherNonCurrentAssets;

        /**
         * 非流动资产合计
         */
        private BigDecimal totalNonCurrentAssets;
    }

    /**
     * 流动负债
     */
    @Data
    public static class CurrentLiabilities implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 应付账款
         */
        private BigDecimal accountsPayable;

        /**
         * 预收款项
         */
        private BigDecimal advancePayments;

        /**
         * 应付职工薪酬
         */
        private BigDecimal employeePayables;

        /**
         * 应交税费
         */
        private BigDecimal taxPayables;

        /**
         * 其他应付款
         */
        private BigDecimal otherPayables;

        /**
         * 流动负债合计
         */
        private BigDecimal totalCurrentLiabilities;
    }

    /**
     * 非流动负债
     */
    @Data
    public static class NonCurrentLiabilities implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 长期借款
         */
        private BigDecimal longTermLoans;

        /**
         * 长期应付款
         */
        private BigDecimal longTermPayables;

        /**
         * 其他非流动负债
         */
        private BigDecimal otherNonCurrentLiabilities;

        /**
         * 非流动负债合计
         */
        private BigDecimal totalNonCurrentLiabilities;
    }

    /**
     * 所有者权益
     */
    @Data
    public static class OwnersEquity implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 实收资本
         */
        private BigDecimal paidInCapital;

        /**
         * 资本公积
         */
        private BigDecimal capitalReserve;

        /**
         * 盈余公积
         */
        private BigDecimal surplusReserve;

        /**
         * 未分配利润
         */
        private BigDecimal unappropriatedProfit;

        /**
         * 所有者权益合计
         */
        private BigDecimal totalOwnersEquity;
    }

    /**
     * 币种资产负债
     */
    @Data
    public static class CurrencyBalance implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 币种
         */
        private CurrencyEnum currency;

        /**
         * 资产总计
         */
        private BigDecimal totalAssets;

        /**
         * 负债总计
         */
        private BigDecimal totalLiabilities;

        /**
         * 所有者权益
         */
        private BigDecimal ownersEquity;
    }

    /**
     * 部门资产负债
     */
    @Data
    public static class DepartmentBalance implements Serializable {
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
         * 资产总计
         */
        private BigDecimal totalAssets;

        /**
         * 负债总计
         */
        private BigDecimal totalLiabilities;

        /**
         * 所有者权益
         */
        private BigDecimal ownersEquity;

        /**
         * 资产占比
         */
        private BigDecimal assetsPercentage;

        /**
         * 负债占比
         */
        private BigDecimal liabilitiesPercentage;
    }

    /**
     * 资产项目
     */
    @Data
    public static class Asset implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // ... existing code ...
    }

    /**
     * 负债项目
     */
    @Data
    public static class Liability implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // ... existing code ...
    }

    /**
     * 所有者权益项目
     */
    @Data
    public static class Equity implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // ... existing code ...
    }

    /**
     * 历史对比数据
     */
    @Data
    public static class HistoricalComparison implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // ... existing code ...
    }
} 