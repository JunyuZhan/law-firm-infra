package com.lawfirm.cases.repository;

import com.lawfirm.model.cases.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    Optional<Case> findByCaseNo(String caseNo);
} 