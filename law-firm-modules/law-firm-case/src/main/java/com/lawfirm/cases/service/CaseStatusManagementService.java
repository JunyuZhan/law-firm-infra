package com.lawfirm.cases.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.cases.model.vo.CaseStatusChangeVO;

import java.util.List;

public interface CaseStatusManagementService extends IService<Case> {
    
    List<CaseStatusChangeVO> getStatusHistory(Long caseId);
    
    CaseStatusChangeVO getCurrentStatus(Long caseId);
    
    List<String> getAvailableStatus(Long caseId);
    
    CaseStatusChangeVO updateStatus(Long caseId, String status, String reason, String operator);
    
    CaseDetailVO updateProgress(Long caseId, CaseProgressEnum progress, String operator);
    
    CaseProgressEnum getCurrentProgress(Long caseId);
    
    List<CaseProgressEnum> getAvailableProgress(Long caseId);
    
    void archiveCase(Long id, String operator);
    
    void reopenCase(Long id, String operator, String reason);
    
    void suspendCase(Long id, String operator, String reason);
    
    void resumeCase(Long id, String operator);
} 