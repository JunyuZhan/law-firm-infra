package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 财务报表实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_financial_report")
public class FinancialReport extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 报表编号
     */
    @NotBlank(message = "报表编号不能为空")
    @Size(max = 32, message = "报表编号长度不能超过32个字符")
    @TableField("report_number")
    private String reportNumber;

    /**
     * 报表名称
     */
    @NotBlank(message = "报表名称不能为空")
    @Size(max = 100, message = "报表名称长度不能超过100个字符")
    @TableField("report_name")
    private String reportName;

    /**
     * 报表类型（1-资产负债表，2-利润表，3-现金流量表，4-其他）
     */
    @NotNull(message = "报表类型不能为空")
    @TableField("report_type")
    private Integer reportType;

    /**
     * 报表期间（YYYYMM）
     */
    @NotBlank(message = "报表期间不能为空")
    @Size(max = 6, message = "报表期间长度不能超过6个字符")
    @TableField("report_period")
    private String reportPeriod;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    @TableField("currency")
    private CurrencyEnum currency;

    /**
     * 总资产
     */
    @TableField("total_assets")
    private BigDecimal totalAssets;

    /**
     * 总负债
     */
    @TableField("total_liabilities")
    private BigDecimal totalLiabilities;

    /**
     * 所有者权益
     */
    @TableField("total_equity")
    private BigDecimal totalEquity;

    /**
     * 营业收入
     */
    @TableField("operating_income")
    private BigDecimal operatingIncome;

    /**
     * 营业成本
     */
    @TableField("operating_cost")
    private BigDecimal operatingCost;

    /**
     * 营业利润
     */
    @TableField("operating_profit")
    private BigDecimal operatingProfit;

    /**
     * 净利润
     */
    @TableField("net_profit")
    private BigDecimal netProfit;

    /**
     * 经营活动现金流量
     */
    @TableField("operating_cash_flow")
    private BigDecimal operatingCashFlow;

    /**
     * 投资活动现金流量
     */
    @TableField("investing_cash_flow")
    private BigDecimal investingCashFlow;

    /**
     * 筹资活动现金流量
     */
    @TableField("financing_cash_flow")
    private BigDecimal financingCashFlow;

    /**
     * 报表状态（0-草稿，1-已提交，2-已审核）
     */
    @TableField("report_status")
    private Integer reportStatus;

    /**
     * 生成时间
     */
    @TableField("generate_time")
    private LocalDateTime generateTime;

    /**
     * 审核人ID
     */
    @TableField("auditor_id")
    private Long auditorId;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private LocalDateTime auditTime;

    /**
     * 审核意见
     */
    @Size(max = 500, message = "审核意见长度不能超过500个字符")
    @TableField("audit_comment")
    private String auditComment;

    /**
     * 报表说明
     */
    @Size(max = 500, message = "报表说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 报表数据，JSON格式
     */
    @TableField("report_data")
    private String reportData;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.generateTime == null) {
            this.generateTime = LocalDateTime.now();
        }
        if (this.reportStatus == null) {
            this.reportStatus = 0;
        }
    }
} 