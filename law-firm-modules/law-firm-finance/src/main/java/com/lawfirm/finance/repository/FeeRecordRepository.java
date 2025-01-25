package com.lawfirm.finance.repository;

import com.lawfirm.finance.entity.FeeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRecordRepository extends JpaRepository<FeeRecord, Long>, JpaSpecificationExecutor<FeeRecord> {
    
    List<FeeRecord> findByCaseId(Long caseId);
    
    List<FeeRecord> findByClientId(Long clientId);
    
    List<FeeRecord> findByLawFirmId(Long lawFirmId);
    
    List<FeeRecord> findByFeeStatus(String feeStatus);
    
    List<FeeRecord> findByFeeType(String feeType);
} 