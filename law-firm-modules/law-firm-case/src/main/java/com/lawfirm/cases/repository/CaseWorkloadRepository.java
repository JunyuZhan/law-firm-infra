package com.lawfirm.cases.repository;

import com.lawfirm.model.cases.entity.CaseWorkload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CaseWorkloadRepository extends JpaRepository<CaseWorkload, Long> {
    
    List<CaseWorkload> findByCaseIdOrderByStartTimeDesc(Long caseId);
    
    List<CaseWorkload> findByLawyerAndStartTimeBetweenOrderByStartTimeDesc(
            String lawyer, LocalDateTime startDate, LocalDateTime endDate);
    
    List<CaseWorkload> findByLawyerAndNeedReimbursementTrueOrderByStartTimeDesc(String lawyer);
    
    List<CaseWorkload> findByLawyerAndStartTimeBetweenAndNeedReimbursementTrue(
            String lawyer, LocalDateTime startDate, LocalDateTime endDate);
} 