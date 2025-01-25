package com.lawfirm.finance.service.impl;

import com.lawfirm.finance.entity.FeeRecord;
import com.lawfirm.finance.repository.FeeRecordRepository;
import com.lawfirm.finance.service.FeeRecordService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeRecordServiceImpl implements FeeRecordService {

    private final FeeRecordRepository feeRecordRepository;

    @Override
    @Transactional
    public FeeRecord createFeeRecord(FeeRecord feeRecord) {
        feeRecord.setFeeStatus("UNPAID");
        feeRecord.setPaidAmount(BigDecimal.ZERO);
        return feeRecordRepository.save(feeRecord);
    }

    @Override
    @Transactional
    public FeeRecord updateFeeRecord(FeeRecord feeRecord) {
        if (!feeRecordRepository.existsById(feeRecord.getId())) {
            throw new EntityNotFoundException("收费记录不存在");
        }
        return feeRecordRepository.save(feeRecord);
    }

    @Override
    public FeeRecord getFeeRecord(Long id) {
        return feeRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("收费记录不存在"));
    }

    @Override
    @Transactional
    public void deleteFeeRecord(Long id) {
        feeRecordRepository.deleteById(id);
    }

    @Override
    public Page<FeeRecord> findFeeRecords(Pageable pageable) {
        return feeRecordRepository.findAll(pageable);
    }

    @Override
    public List<FeeRecord> findFeeRecordsByCaseId(Long caseId) {
        return feeRecordRepository.findByCaseId(caseId);
    }

    @Override
    public List<FeeRecord> findFeeRecordsByClientId(Long clientId) {
        return feeRecordRepository.findByClientId(clientId);
    }

    @Override
    @Transactional
    public FeeRecord updatePaymentStatus(Long id, String status, BigDecimal paidAmount) {
        FeeRecord feeRecord = getFeeRecord(id);
        
        // 更新支付状态
        feeRecord.setFeeStatus(status);
        feeRecord.setPaidAmount(paidAmount);
        
        if (paidAmount.compareTo(feeRecord.getAmount()) >= 0) {
            feeRecord.setFeeStatus("PAID");
            feeRecord.setPaymentTime(LocalDateTime.now());
        } else if (paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            feeRecord.setFeeStatus("PARTIAL");
        }
        
        return feeRecordRepository.save(feeRecord);
    }

    @Override
    public BigDecimal calculateTotalAmount(Long lawFirmId) {
        return feeRecordRepository.findByLawFirmId(lawFirmId).stream()
                .map(FeeRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalPaidAmount(Long lawFirmId) {
        return feeRecordRepository.findByLawFirmId(lawFirmId).stream()
                .map(FeeRecord::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 