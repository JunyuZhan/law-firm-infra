package com.lawfirm.cases.service.impl;

import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.cases.model.entity.CaseAssignment;
import com.lawfirm.cases.repository.CaseAssignmentRepository;
import com.lawfirm.cases.repository.CaseRepository;
import com.lawfirm.cases.service.CaseAssignmentService;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseAssignmentServiceImpl implements CaseAssignmentService {

    private final CaseRepository caseRepository;
    private final CaseAssignmentRepository assignmentRepository;

    @Override
    @Transactional
    public CaseAssignment assignCase(Long caseId, String toLawyer, String reason,
                                   LocalDateTime expectedHandoverTime, String operator) {
        Case caseInfo = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with id: " + caseId));

        // 检查是否有未完成的交接
        if (assignmentRepository.existsByCaseIdAndAssignmentStatusNot(caseId, "COMPLETED")) {
            throw new BusinessException("Case has pending assignment");
        }

        CaseAssignment assignment = new CaseAssignment();
        assignment.setCaseId(caseId);
        assignment.setFromLawyer(caseInfo.getLawyer());
        assignment.setToLawyer(toLawyer);
        assignment.setReason(reason);
        assignment.setAssignTime(LocalDateTime.now());
        assignment.setExpectedHandoverTime(expectedHandoverTime);
        assignment.setAssignmentStatus("PENDING");
        assignment.setOperator(operator);

        assignmentRepository.save(assignment);
        log.info("Case assigned from {} to {}, caseId: {}", 
                caseInfo.getLawyer(), toLawyer, caseId);
        return assignment;
    }

    @Override
    @Transactional
    public void startHandover(Long assignmentId, String operator) {
        CaseAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));

        if (!"PENDING".equals(assignment.getAssignmentStatus())) {
            throw new BusinessException("Assignment status is not PENDING");
        }

        assignment.setAssignmentStatus("IN_PROGRESS");
        assignment.setOperator(operator);
        assignmentRepository.save(assignment);
        log.info("Case handover started, assignmentId: {}", assignmentId);
    }

    @Override
    @Transactional
    public void completeHandover(Long assignmentId, String operator) {
        CaseAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));

        if (!"IN_PROGRESS".equals(assignment.getAssignmentStatus())) {
            throw new BusinessException("Assignment status is not IN_PROGRESS");
        }

        Case caseInfo = caseRepository.findById(assignment.getCaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with id: " + assignment.getCaseId()));

        // 更新案件负责律师
        caseInfo.setLawyer(assignment.getToLawyer());
        caseRepository.save(caseInfo);

        // 更新交接状态
        assignment.setAssignmentStatus("COMPLETED");
        assignment.setActualHandoverTime(LocalDateTime.now());
        assignment.setOperator(operator);
        assignmentRepository.save(assignment);
        
        log.info("Case handover completed, assignmentId: {}", assignmentId);
    }

    @Override
    public List<CaseAssignment> getCaseAssignments(Long caseId) {
        return assignmentRepository.findByCaseIdOrderByAssignTimeDesc(caseId);
    }

    @Override
    public List<CaseAssignment> getPendingAssignments(String lawyer) {
        return assignmentRepository.findByToLawyerAndAssignmentStatus(lawyer, "PENDING");
    }

    @Override
    public List<CaseAssignment> getHandoverAssignments(String lawyer) {
        return assignmentRepository.findByFromLawyerAndAssignmentStatusNot(lawyer, "COMPLETED");
    }
} 