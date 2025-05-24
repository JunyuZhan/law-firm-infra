package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 分析请求参数DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisRequestDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    /** 分析类型 */
    private String analysisType;
    /** 起始日期 */
    private LocalDate startDate;
    /** 结束日期 */
    private LocalDate endDate;
    /** 其他筛选条件 */
    private String filter;
}