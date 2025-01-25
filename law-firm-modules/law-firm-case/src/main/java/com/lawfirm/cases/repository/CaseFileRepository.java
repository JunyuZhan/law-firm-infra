package com.lawfirm.cases.repository;

import com.lawfirm.model.cases.entity.CaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseFileRepository extends JpaRepository<CaseFile, Long> {
    
    List<CaseFile> findByCaseId(Long caseId);
    
    List<CaseFile> findByCaseIdAndIsConfidential(Long caseId, Boolean isConfidential);
} 