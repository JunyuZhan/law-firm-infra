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
 * 现金流量表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CashFlowReportVO extends BaseVO {

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
     * 经营活动现金流入
     */
    private BigDecimal operatingCashInflow;

    /**
     * 经营活动现金流出
     */
    private BigDecimal operatingCashOutflow;

    /**
     * 经营活动现金流量净额
     */
    private BigDecimal netOperatingCashFlow;

    /**
     * 投资活动现金流入
     */
    private BigDecimal investingCashInflow;

    /**
     * 投资活动现金流出
     */
    private BigDecimal investingCashOutflow;

    /**
     * 投资活动现金流量净额
     */
    private BigDecimal netInvestingCashFlow;

    /**
     * 筹资活动现金流入
     */
    private BigDecimal financingCashInflow;

    /**
     * 筹资活动现金流出
     */
    private BigDecimal financingCashOutflow;

    /**
     * 筹资活动现金流量净额
     */
    private BigDecimal netFinancingCashFlow;

    /**
     * 现金及现金等价物净增加额
     */
    private BigDecimal netCashIncrease;

    /**
     * 期初现金及现金等价物余额
     */
    private BigDecimal beginningCashBalance;

    /**
     * 期末现金及现金等价物余额
     */
    private BigDecimal endingCashBalance;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, CurrencyCashFlow> currencyCashFlow;

    /**
     * 按部门统计
     */
    private transient List<DepartmentCashFlow> departmentCashFlow;

    /**
     * 按业务线统计
     */
    private transient List<BusinessLineCashFlow> businessLineCashFlow;

    /**
     * 币种现金流量
     */
    @Data
    public static class CurrencyCashFlow implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 币种
         */
        private CurrencyEnum currency;

        /**
         * 经营活动现金流量净额
         */
        private BigDecimal netOperatingCashFlow;

        /**
         * 投资活动现金流量净额
         */
        private BigDecimal netInvestingCashFlow;

        /**
         * 筹资活动现金流量净额
         */
        private BigDecimal netFinancingCashFlow;

        /**
         * 现金净增加额
         */
        private BigDecimal netCashIncrease;

        /**
         * 期末现金余额
         */
        private BigDecimal endingCashBalance;
    }

    /**
     * 部门现金流量
     */
    @Data
    public static class DepartmentCashFlow implements Serializable {
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
         * 经营活动现金流量净额
         */
        private BigDecimal netOperatingCashFlow;

        /**
         * 投资活动现金流量净额
         */
        private BigDecimal netInvestingCashFlow;

        /**
         * 筹资活动现金流量净额
         */
        private BigDecimal netFinancingCashFlow;

        /**
         * 现金净增加额
         */
        private BigDecimal netCashIncrease;

        /**
         * 现金流量占比
         */
        private BigDecimal cashFlowPercentage;
    }

    /**
     * 业务线现金流量
     */
    @Data
    public static class BusinessLineCashFlow implements Serializable {
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
         * 经营活动现金流量净额
         */
        private BigDecimal netOperatingCashFlow;

        /**
         * 投资活动现金流量净额
         */
        private BigDecimal netInvestingCashFlow;

        /**
         * 筹资活动现金流量净额
         */
        private BigDecimal netFinancingCashFlow;

        /**
         * 现金净增加额
         */
        private BigDecimal netCashIncrease;

        /**
         * 现金流量占比
         */
        private BigDecimal cashFlowPercentage;
    }
} 