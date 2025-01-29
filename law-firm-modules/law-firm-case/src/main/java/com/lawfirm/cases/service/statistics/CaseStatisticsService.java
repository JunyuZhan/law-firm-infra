package com.lawfirm.cases.service.statistics;

import com.lawfirm.model.cases.enums.*;
import java.util.Map;

public interface CaseStatisticsService {
    
    Map<CaseStatusEnum, Long> countByStatus();
    
    Map<CaseImportanceEnum, Long> getImportanceStatistics();
    
    Map<CasePriorityEnum, Long> getPriorityStatistics();
    
    Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics();
    
    Map<CaseSourceEnum, Long> getSourceStatistics();
} 