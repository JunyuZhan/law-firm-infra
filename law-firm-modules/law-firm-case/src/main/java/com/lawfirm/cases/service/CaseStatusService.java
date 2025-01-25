package com.lawfirm.cases.service;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.vo.CaseStatusVO;

import java.util.List;

/**
 * 案件状态服务接口
 */
public interface CaseStatusService {
    
    /**
     * 变更案件状态
     */
    void transitStatus(Long caseId, CaseStatusEnum targetStatus, String operator, String reason);
    
    /**
     * 检查是否可以变更到目标状态
     */
    boolean canTransitTo(Long caseId, CaseStatusEnum targetStatus);
    
    /**
     * 获取当前状态
     */
    CaseStatusEnum getCurrentStatus(Long caseId);

    /**
     * 获取案件状态列表
     */
    List<CaseStatusVO> getCaseStatus();
} 