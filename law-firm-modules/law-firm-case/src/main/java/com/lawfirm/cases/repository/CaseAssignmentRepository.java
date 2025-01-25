package com.lawfirm.cases.repository;

import com.lawfirm.cases.model.entity.CaseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseAssignmentRepository extends JpaRepository<CaseAssignment, Long> {
    
    boolean existsByCaseIdAndAssignmentStatusNot(Long caseId, String status);
    
    List<CaseAssignment> findByCaseIdOrderByAssignTimeDesc(Long caseId);
    
    List<CaseAssignment> findByToLawyerAndAssignmentStatus(String lawyer, String status);
    
    List<CaseAssignment> findByFromLawyerAndAssignmentStatusNot(String lawyer, String status);
} 