package com.lawfirm.model.analysis.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 案件分析查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CaseAnalysisQueryDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String caseType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long clientId;
    private Long leadAttorneyId;
    private String status;
} 