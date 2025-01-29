package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CaseQueryService extends IService<Case> {
    
    List<CaseDetailVO> findByLawyer(String lawyer, CaseQueryDTO query);
    
    List<CaseDetailVO> findByClient(Long clientId, CaseQueryDTO query);
    
    List<CaseDetailVO> findRelatedCases(Long caseId);
    
    Page<CaseDetailVO> findCases(CaseQueryDTO query, Pageable pageable);
    
    List<CaseDetailVO> findByCurrentLawyer();
    
    List<Case> findCasesByStatus(CaseStatusEnum status);
    
    List<Case> findCasesByType(String caseType);
    
    List<Case> findMajorCases();
    
    List<Case> findConflictCases();
    
    List<Case> findCasesByProgress(CaseProgressEnum progress);
    
    List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType);
    
    List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty);
    
    List<Case> findCasesByImportance(CaseImportanceEnum importance);
    
    List<Case> findCasesByPriority(CasePriorityEnum priority);
    
    List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType);
    
    List<Case> findCasesBySource(CaseSourceEnum source);
} 