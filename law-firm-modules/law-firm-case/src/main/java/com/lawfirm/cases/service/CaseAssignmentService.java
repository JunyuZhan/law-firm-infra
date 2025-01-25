package com.lawfirm.cases.service;

import com.lawfirm.cases.model.entity.CaseAssignment;

import java.time.LocalDateTime;
import java.util.List;

public interface CaseAssignmentService {
    
    CaseAssignment assignCase(Long caseId, String toLawyer, String reason, 
                            LocalDateTime expectedHandoverTime, String operator);
    
    void startHandover(Long assignmentId, String operator);
    
    void completeHandover(Long assignmentId, String operator);
    
    List<CaseAssignment> getCaseAssignments(Long caseId);
    
    List<CaseAssignment> getPendingAssignments(String lawyer);
    
    List<CaseAssignment> getHandoverAssignments(String lawyer);
} 