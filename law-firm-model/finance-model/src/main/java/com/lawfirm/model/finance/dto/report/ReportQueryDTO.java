package com.lawfirm.model.finance.dto.report;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 报表查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReportQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 报表编号
     */
    private String reportNumber;

    /**
     * 报表名称
     */
    private String reportName;

    /**
     * 报表类型
     */
    private Integer reportType;

    /**
     * 报表期间
     */
    private String reportPeriod;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 报表状态
     */
    private Integer reportStatus;

    /**
     * 生成时间开始
     */
    private LocalDateTime generateTimeStart;

    /**
     * 生成时间结束
     */
    private LocalDateTime generateTimeEnd;

    /**
     * 审核人ID
     */
    private Long auditorId;

    /**
     * 审核时间开始
     */
    private LocalDateTime auditTimeStart;

    /**
     * 审核时间结束
     */
    private LocalDateTime auditTimeEnd;

    /**
     * 关联部门ID
     */
    private Long departmentId;

    /**
     * 创建时间开始
     */
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 