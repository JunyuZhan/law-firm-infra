package com.lawfirm.model.finance.vo.expense;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.ExpenseTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 支出统计VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ExpenseStatisticsVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 总支出金额
     */
    private BigDecimal totalAmount;

    /**
     * 按支出类型统计
     */
    private transient Map<ExpenseTypeEnum, ExpenseTypeStatistics> expenseTypeStatistics;

    /**
     * 按部门统计
     */
    private transient List<DepartmentExpenseStatistics> departmentStatistics;

    /**
     * 按成本中心统计
     */
    private transient List<CostCenterExpenseStatistics> costCenterStatistics;

    /**
     * 按员工统计
     */
    private transient List<EmployeeExpenseStatistics> employeeStatistics;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, BigDecimal> currencyStatistics;

    /**
     * 按月度统计
     */
    private transient List<MonthlyExpenseStatistics> monthlyStatistics;

    /**
     * 支出类型统计
     */
    @Data
    public static class ExpenseTypeStatistics implements Serializable {
        private static final long serialVersionUID = 1L;
        
        /**
         * 支出类型
         */
        private ExpenseTypeEnum expenseType;

        /**
         * 支出金额
         */
        private BigDecimal amount;

        /**
         * 支出笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 部门支出统计
     */
    @Data
    public static class DepartmentExpenseStatistics implements Serializable {
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
         * 支出金额
         */
        private BigDecimal amount;

        /**
         * 支出笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 成本中心支出统计
     */
    @Data
    public static class CostCenterExpenseStatistics implements Serializable {
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
         * 支出金额
         */
        private BigDecimal amount;

        /**
         * 支出笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 员工支出统计
     */
    @Data
    public static class EmployeeExpenseStatistics implements Serializable {
        private static final long serialVersionUID = 1L;
        
        /**
         * 员工ID
         */
        private Long employeeId;

        /**
         * 员工姓名
         */
        private String employeeName;

        /**
         * 支出金额
         */
        private BigDecimal amount;

        /**
         * 支出笔数
         */
        private Integer count;

        /**
         * 占比
         */
        private BigDecimal percentage;
    }

    /**
     * 月度支出统计
     */
    @Data
    public static class MonthlyExpenseStatistics implements Serializable {
        private static final long serialVersionUID = 1L;
        
        /**
         * 年月（格式：yyyy-MM）
         */
        private String yearMonth;

        /**
         * 支出金额
         */
        private BigDecimal amount;

        /**
         * 支出笔数
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