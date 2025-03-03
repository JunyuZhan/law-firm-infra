package com.lawfirm.model.finance.dto.report;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 报表创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReportCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 报表编号
     */
    @NotBlank(message = "报表编号不能为空")
    @Size(max = 32, message = "报表编号长度不能超过32个字符")
    private String reportNumber;

    /**
     * 报表名称
     */
    @NotBlank(message = "报表名称不能为空")
    @Size(max = 100, message = "报表名称长度不能超过100个字符")
    private String reportName;

    /**
     * 报表类型
     */
    @NotNull(message = "报表类型不能为空")
    private Integer reportType;

    /**
     * 报表期间
     */
    @NotBlank(message = "报表期间不能为空")
    @Size(max = 6, message = "报表期间长度不能超过6个字符")
    private String reportPeriod;

    /**
     * 币种
     */
    @NotNull(message = "币种不能为空")
    private CurrencyEnum currency;

    /**
     * 总资产
     */
    private BigDecimal totalAssets;

    /**
     * 总负债
     */
    private BigDecimal totalLiabilities;

    /**
     * 总权益
     */
    private BigDecimal totalEquity;

    /**
     * 营业收入
     */
    private BigDecimal operatingIncome;

    /**
     * 营业成本
     */
    private BigDecimal operatingCost;

    /**
     * 营业利润
     */
    private BigDecimal operatingProfit;

    /**
     * 净利润
     */
    private BigDecimal netProfit;

    /**
     * 经营现金流
     */
    private BigDecimal operatingCashFlow;

    /**
     * 投资现金流
     */
    private BigDecimal investingCashFlow;

    /**
     * 筹资现金流
     */
    private BigDecimal financingCashFlow;

    /**
     * 报表状态
     */
    private Integer reportStatus;

    /**
     * 生成时间
     */
    private LocalDateTime generateTime;

    /**
     * 审核人ID
     */
    private Long auditorId;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核意见
     */
    @Size(max = 500, message = "审核意见长度不能超过500个字符")
    private String auditComment;

    /**
     * 报表说明
     */
    @Size(max = 500, message = "报表说明长度不能超过500个字符")
    private String description;

    /**
     * 报表数据（JSON格式）
     */
    private String reportData;

    /**
     * 关联部门ID
     */
    private Long departmentId;
} 