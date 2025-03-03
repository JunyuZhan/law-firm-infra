package com.lawfirm.model.finance.vo.income;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.IncomeTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 收入统计VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IncomeStatisticsVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 总收入金额
     */
    private BigDecimal totalAmount;

    /**
     * 按收入类型统计
     */
    private transient Map<IncomeTypeEnum, IncomeTypeStatistics> incomeTypeStatistics;

    /**
     * 按部门统计
     */
    private transient List<DepartmentIncomeStatistics> departmentStatistics;

    /**
     * 按律师统计
     */
    private transient List<LawyerIncomeStatistics> lawyerStatistics;

    /**
     * 按客户统计
     */
    private transient List<ClientIncomeStatistics> clientStatistics;

    /**
     * 按案件类型统计
     */
    private transient List<CaseTypeIncomeStatistics> caseTypeStatistics;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, BigDecimal> currencyStatistics;

    /**
     * 按月度统计
     */
    private transient List<MonthlyIncomeStatistics> monthlyStatistics;

    /**
     * 收入类型统计
     */
    @Data
    public static class IncomeTypeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 收入类型
         */
        private IncomeTypeEnum incomeType;

        /**
         * 收入金额
         */
        private BigDecimal amount;

        /**
         * 收入笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 部门收入统计
     */
    @Data
    public static class DepartmentIncomeStatistics implements Serializable {
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
         * 收入金额
         */
        private BigDecimal amount;

        /**
         * 收入笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 律师收入统计
     */
    @Data
    public static class LawyerIncomeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 律师ID
         */
        private Long lawyerId;

        /**
         * 律师名称
         */
        private String lawyerName;

        /**
         * 收入金额
         */
        private BigDecimal amount;

        /**
         * 收入笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 客户收入统计
     */
    @Data
    public static class ClientIncomeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 客户ID
         */
        private Long clientId;

        /**
         * 客户名称
         */
        private String clientName;

        /**
         * 收入金额
         */
        private BigDecimal amount;

        /**
         * 收入笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 案件类型收入统计
     */
    @Data
    public static class CaseTypeIncomeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 案件类型编码
         */
        private String caseTypeCode;

        /**
         * 案件类型名称
         */
        private String caseTypeName;

        /**
         * 收入金额
         */
        private BigDecimal amount;

        /**
         * 收入笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 月度收入统计
     */
    @Data
    public static class MonthlyIncomeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 年月（格式：yyyy-MM）
         */
        private String yearMonth;

        /**
         * 收入金额
         */
        private BigDecimal amount;

        /**
         * 收入笔数
         */
        private Integer count;

        /**
         * 环比增长率
         */
        private BigDecimal monthOnMonthRate;

        /**
         * 同比增长率
         */
        private BigDecimal yearOnYearRate;
    }
} 