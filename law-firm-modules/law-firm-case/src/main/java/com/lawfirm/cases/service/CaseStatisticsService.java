package com.lawfirm.cases.service;

import java.time.LocalDateTime;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;

/**
 * 案件统计服务接口
 */
public interface CaseStatisticsService extends IService<Case> {
    
    /**
     * 按状态统计案件数量
     */
    Map<CaseStatusEnum, Long> countByStatus();
    
    /**
     * 按类型统计案件数量
     */
    Map<CaseTypeEnum, Long> countByType();
    
    /**
     * 按律师统计案件数量
     */
    Map<String, Long> countByLawyer();
    
    /**
     * 按客户统计案件数量
     */
    Map<Long, Long> countByClient();
    
    /**
     * 按状态统计案件数量
     */
    long countCasesByStatus(CaseStatusEnum status);
    
    /**
     * 按律师统计案件数量
     */
    long countCasesByLawyer(String lawyer);
    
    /**
     * 按日期范围统计案件数量
     */
    long countCasesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 获取进度统计
     */
    Map<CaseProgressEnum, Long> getProgressStatistics();
    
    /**
     * 获取处理类型统计
     */
    Map<CaseHandleTypeEnum, Long> getHandleTypeStatistics();
    
    /**
     * 获取难度统计
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
     * 获取收费类型统计
     */
    Map<CaseFeeTypeEnum, Long> getFeeTypeStatistics();
    
    /**
     * 获取来源统计
     */
    Map<CaseSourceEnum, Long> getSourceStatistics();
} 