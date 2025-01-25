package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.cases.model.dto.CaseCreateDTO;
import com.lawfirm.cases.model.dto.CaseQueryDTO;
import com.lawfirm.cases.model.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 案件服务接口
 */
public interface CaseService extends IService<Case> {
    
    /**
     * 创建案件
     */
    CaseDetailVO createCase(CaseCreateDTO createDTO);
    
    /**
     * 更新案件
     */
    CaseDetailVO updateCase(Long id, CaseUpdateDTO updateDTO);
    
    /**
     * 删除案件
     */
    void deleteCase(Long id);
    
    /**
     * 根据ID获取案件
     */
    CaseDetailVO getCaseById(Long id);
    
    /**
     * 根据案号获取案件
     */
    CaseDetailVO getCaseByCaseNumber(String caseNumber);
    
    /**
     * 分页查询案件
     */
    Page<CaseDetailVO> findCases(CaseQueryDTO queryDTO, Pageable pageable);
    
    /**
     * 根据ID获取案件实体
     * 内部使用,不暴露给Controller
     */
    Case getById(Long id);
    
    Optional<Case> getCaseByCaseNo(String caseNo);
    
    List<Case> findCasesByLawyer(String lawyer);
    
    List<Case> findCasesByClient(String client);
    
    List<Case> findCasesByType(String caseType);
    
    List<Case> findCasesByStatus(CaseStatusEnum status);
    
    List<Case> findMajorCases();
    
    List<Case> findConflictCases();
    
    void validateCase(Long id);
    
    void archiveCase(Long id, String operator);
    
    void reopenCase(Long id, String operator, String reason);
    
    void suspendCase(Long id, String operator, String reason);
    
    void resumeCase(Long id, String operator);
    
    long countCasesByStatus(CaseStatusEnum status);
    
    long countCasesByLawyer(String lawyer);
    
    long countCasesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 获取案件状态历史
     */
    List<CaseStatusVO> getStatusHistory(Long caseId);
    
    /**
     * 获取案件当前状态
     */
    CaseStatusVO getCurrentStatus(Long caseId);
    
    /**
     * 获取可用状态列表
     */
    List<String> getAvailableStatus(Long caseId);
    
    /**
     * 更新案件状态
     */
    CaseStatusVO updateStatus(Long caseId, String status, String reason, String operator);

    /**
     * 获取案件状态统计
     */
    Map<CaseStatusEnum, Long> getCaseStatistics();

    /**
     * 更新案件进展
     */
    CaseDetailVO updateProgress(Long caseId, CaseProgressEnum progress, String operator);

    /**
     * 获取案件当前进展
     */
    CaseProgressEnum getCurrentProgress(Long caseId);

    /**
     * 获取可用进展列表
     */
    List<CaseProgressEnum> getAvailableProgress(Long caseId);

    /**
     * 更新办理方式
     */
    CaseDetailVO updateHandleType(Long caseId, CaseHandleTypeEnum handleType, String operator);

    /**
     * 更新案件难度
     */
    CaseDetailVO updateDifficulty(Long caseId, CaseDifficultyEnum difficulty, String operator);

    /**
     * 更新案件重要程度
     */
    CaseDetailVO updateImportance(Long caseId, CaseImportanceEnum importance, String operator);

    /**
     * 更新案件优先级
     */
    CaseDetailVO updatePriority(Long caseId, CasePriorityEnum priority, String operator);

    /**
     * 更新收费方式
     */
    CaseDetailVO updateFeeType(Long caseId, CaseFeeTypeEnum feeType, String operator);

    /**
     * 更新案件来源
     */
    CaseDetailVO updateSource(Long caseId, CaseSourceEnum source, String operator);

    /**
     * 根据进展状态查询案件
     */
    List<Case> findCasesByProgress(CaseProgressEnum progress);

    /**
     * 根据办理方式查询案件
     */
    List<Case> findCasesByHandleType(CaseHandleTypeEnum handleType);

    /**
     * 根据难度等级查询案件
     */
    List<Case> findCasesByDifficulty(CaseDifficultyEnum difficulty);

    /**
     * 根据重要程度查询案件
     */
    List<Case> findCasesByImportance(CaseImportanceEnum importance);

    /**
     * 根据优先级查询案件
     */
    List<Case> findCasesByPriority(CasePriorityEnum priority);

    /**
     * 根据收费方式查询案件
     */
    List<Case> findCasesByFeeType(CaseFeeTypeEnum feeType);

    /**
     * 根据案件来源查询案件
     */
    List<Case> findCasesBySource(CaseSourceEnum source);

    /**
     * 获取案件进展统计
     */
    Map<CaseProgressEnum, Long> getProgressStatistics();

    /**
     * 获取办理方式统计
     */
    Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics();

    /**
     * 获取难度等级统计
     */
    Map<CaseDifficultyEnum, Long> getDifficultyStatistics();

    /**
     * 获取重要程度统计
     */
    Map<CaseImportanceEnum, Long> getImportanceStatistics();

    /**
     * 获取优先级统计
     */
    Map<CasePriorityEnum, Long> getPriorityStatistics();

    /**
     * 获取收费方式统计
     */
    Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics();

    /**
     * 获取案件来源统计
     */
    Map<CaseSourceEnum, Long> getSourceStatistics();
} 