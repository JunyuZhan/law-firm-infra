package com.lawfirm.cases.service;

import com.lawfirm.model.cases.entity.CaseWorkload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CaseWorkloadService {
    
    CaseWorkload recordWorkload(Long caseId, String lawyer, String workType, String workContent,
                              LocalDateTime startTime, LocalDateTime endTime, String location,
                              Boolean needReimbursement, BigDecimal reimbursementAmount,
                              String reimbursementDesc, String remarks);
    
    void deleteWorkload(Long workloadId);
    
    List<CaseWorkload> getCaseWorkloads(Long caseId);
    
    List<CaseWorkload> getLawyerWorkloads(String lawyer, LocalDateTime startDate, LocalDateTime endDate);
    
    Map<String, BigDecimal> getLawyerWorkloadStats(String lawyer, LocalDateTime startDate, LocalDateTime endDate);
    
    List<CaseWorkload> getPendingReimbursements(String lawyer);
    
    BigDecimal getTotalReimbursementAmount(String lawyer, LocalDateTime startDate, LocalDateTime endDate);
} 