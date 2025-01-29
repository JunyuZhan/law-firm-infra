package com.lawfirm.cases.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.vo.CaseStatusVO;

public interface CaseService extends IService<Case> {
    
    CaseDetailVO createCase(CaseCreateDTO dto);
    
    CaseDetailVO updateCase(Long id, CaseUpdateDTO dto);
    
    void deleteCase(Long id);
    
    CaseDetailVO getCaseById(Long id);
    
    CaseDetailVO getCaseByCaseNumber(String caseNumber);
    
    void validateCase(Long id);

    // 案件查询相关方法
    Page<CaseDetailVO> findCases(CaseQueryDTO queryDTO, Pageable pageable);
    Map<CaseStatusEnum, Long> getCaseStatistics();
    List<CaseDetailVO> findByQuery(CaseQueryDTO queryDTO);
    Page<CaseDetailVO> findByQuery(CaseQueryDTO queryDTO, Pageable pageable);
    List<CaseDetailVO> findByCurrentLawyer();
    List<CaseDetailVO> findByClient(Long clientId, CaseQueryDTO queryDTO);
    List<CaseDetailVO> findRelatedCases(Long caseId);
    
    // 案件状态相关方法
    List<CaseStatusVO> getStatusHistory(Long caseId);
    CaseStatusEnum getCurrentStatus(Long caseId);
    List<CaseStatusEnum> getAvailableStatus(Long caseId);
    void updateStatus(Long caseId, String status, String reason, String remark);
    
    // 案件进展相关方法
    CaseDetailVO updateProgress(Long caseId, CaseProgressEnum progress, String remark);
    CaseProgressEnum getCurrentProgress(Long caseId);
    List<CaseProgressEnum> getAvailableProgress(Long caseId);
    
    // 案件属性更新方法
    CaseDetailVO updateHandleType(Long caseId, CaseHandleTypeEnum handleType, String remark);
    CaseDetailVO updateDifficulty(Long caseId, CaseDifficultyEnum difficulty, String remark);
    CaseDetailVO updateImportance(Long caseId, CaseImportanceEnum importance, String remark);
    CaseDetailVO updatePriority(Long caseId, CasePriorityEnum priority, String remark);
    CaseDetailVO updateFeeType(Long caseId, CaseFeeTypeEnum feeType, String remark);
    CaseDetailVO updateSource(Long caseId, CaseSourceEnum source, String remark);
    
    // 案件分类查询方法
    List<CaseDetailVO> findCasesByProgress(CaseProgressEnum progress);
    List<CaseDetailVO> findCasesByHandleType(CaseHandleTypeEnum handleType);
    List<CaseDetailVO> findCasesByDifficulty(CaseDifficultyEnum difficulty);
    List<CaseDetailVO> findCasesByImportance(CaseImportanceEnum importance);
    
    // 统计相关方法
    Map<CaseStatusEnum, Long> countByStatus();
    Map<CaseTypeEnum, Long> countByType();
    Map<String, Long> countByLawyer();
    Map<String, Long> countByClient();
    Map<CaseProgressEnum, Long> getProgressStatistics();
    Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics();
    Map<CaseDifficultyEnum, Long> getDifficultyStatistics();
    Map<CaseImportanceEnum, Long> getImportanceStatistics();
    Map<CasePriorityEnum, Long> getPriorityStatistics();
    Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics();
    Map<CaseSourceEnum, Long> getSourceStatistics();

    Map<CaseProgressEnum, Long> countByProgress();
    Map<CaseHandleTypeEnum, Long> countByHandleType();
    Map<CaseImportanceEnum, Long> countByImportance();
} 