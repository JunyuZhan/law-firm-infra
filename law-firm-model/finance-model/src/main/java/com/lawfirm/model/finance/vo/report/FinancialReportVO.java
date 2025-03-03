package com.lawfirm.model.finance.vo.report;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 财务报表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FinancialReportVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 报表期间（格式：yyyy-MM）
     */
    private String reportPeriod;

    /**
     * 报表生成时间
     */
    private transient LocalDateTime generateTime;

    /**
     * 总收入
     */
    private BigDecimal totalIncome;

    /**
     * 总支出
     */
    private BigDecimal totalExpense;

    /**
     * 净利润
     */
    private BigDecimal netProfit;

    /**
     * 总资产
     */
    private BigDecimal totalAssets;

    /**
     * 总负债
     */
    private BigDecimal totalLiabilities;

    /**
     * 所有者权益
     */
    private BigDecimal ownerEquity;

    /**
     * 经营活动现金流量
     */
    private BigDecimal operatingCashFlow;

    /**
     * 投资活动现金流量
     */
    private BigDecimal investingCashFlow;

    /**
     * 筹资活动现金流量
     */
    private BigDecimal financingCashFlow;

    /**
     * 现金流量净额
     */
    private BigDecimal netCashFlow;

    /**
     * 按币种统计
     */
    private transient Map<CurrencyEnum, CurrencyReport> currencyReports;

    /**
     * 按部门统计
     */
    private transient List<DepartmentReport> departmentReports;

    /**
     * 按业务线统计
     */
    private transient List<BusinessLineReport> businessLineReports;

    /**
     * 币种报表
     */
    @Data
    public static class CurrencyReport implements Serializable {
        private static final long serialVersionUID = 1L;
        
        /**
         * 币种
         */
        private CurrencyEnum currency;

        /**
         * 收入
         */
        private BigDecimal income;

        /**
         * 支出
         */
        private BigDecimal expense;

        /**
         * 净利润
         */
        private BigDecimal netProfit;

        /**
         * 资产
         */
        private BigDecimal assets;

        /**
         * 负债
         */
        private BigDecimal liabilities;

        /**
         * 所有者权益
         */
        private BigDecimal ownerEquity;
    }

    /**
     * 部门报表
     */
    @Data
    public static class DepartmentReport implements Serializable {
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
         * 收入
         */
        private BigDecimal income;

        /**
         * 支出
         */
        private BigDecimal expense;

        /**
         * 净利润
         */
        private BigDecimal netProfit;

        /**
         * 收入占比
         */
        private BigDecimal incomePercentage;

        /**
         * 支出占比
         */
        private BigDecimal expensePercentage;

        /**
         * 利润占比
         */
        private BigDecimal profitPercentage;
    }

    /**
     * 业务线报表
     */
    @Data
    public static class BusinessLineReport implements Serializable {
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
         * 收入
         */
        private BigDecimal income;

        /**
         * 支出
         */
        private BigDecimal expense;

        /**
         * 净利润
         */
        private BigDecimal netProfit;

        /**
         * 收入占比
         */
        private BigDecimal incomePercentage;

        /**
         * 支出占比
         */
        private BigDecimal expensePercentage;

        /**
         * 利润占比
         */
        private BigDecimal profitPercentage;
    }

    /**
     * 自定义序列化逻辑
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 保存时间戳
        long generateTimeEpoch = generateTime != null ? generateTime.toEpochSecond(ZoneOffset.UTC) : 0;
        
        // 执行默认序列化
        out.defaultWriteObject();
        
        // 写入时间戳
        out.writeLong(generateTimeEpoch);
    }
    
    /**
     * 自定义反序列化逻辑
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 执行默认反序列化
        in.defaultReadObject();
        
        // 读取时间戳并转换回LocalDateTime
        long generateTimeEpoch = in.readLong();
        
        if (generateTimeEpoch > 0) {
            this.generateTime = LocalDateTime.ofEpochSecond(generateTimeEpoch, 0, ZoneOffset.UTC);
        }
    }
} 