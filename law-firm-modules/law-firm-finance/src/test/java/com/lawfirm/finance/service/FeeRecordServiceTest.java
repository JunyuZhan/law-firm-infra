package com.lawfirm.finance.service;

import com.lawfirm.finance.config.TestConfig;
import com.lawfirm.finance.entity.FeeRecord;
import com.lawfirm.finance.repository.FeeRecordRepository;
import com.lawfirm.finance.service.impl.FeeRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class FeeRecordServiceTest {

    @Mock
    private FeeRecordRepository feeRecordRepository;

    @InjectMocks
    private FeeRecordServiceImpl feeRecordService;

    private FeeRecord testFeeRecord;

    @BeforeEach
    void setUp() {
        testFeeRecord = new FeeRecord();
        testFeeRecord.setId(1L);
        testFeeRecord.setAmount(new BigDecimal("1000.00"));
        testFeeRecord.setFeeStatus("UNPAID");
        testFeeRecord.setFeeType("案件收费");
        testFeeRecord.setCaseId(1L);
        testFeeRecord.setClientId(1L);
        testFeeRecord.setLawFirmId(1L);
        testFeeRecord.setDescription("测试收费记录");
    }

    @Test
    void createFeeRecord() {
        when(feeRecordRepository.save(any(FeeRecord.class))).thenReturn(testFeeRecord);

        FeeRecord created = feeRecordService.createFeeRecord(testFeeRecord);

        assertNotNull(created);
        assertEquals(testFeeRecord.getAmount(), created.getAmount());
        assertEquals("UNPAID", created.getFeeStatus());
        verify(feeRecordRepository, times(1)).save(any(FeeRecord.class));
    }

    @Test
    void updateFeeRecord() {
        when(feeRecordRepository.existsById(anyLong())).thenReturn(true);
        when(feeRecordRepository.save(any(FeeRecord.class))).thenReturn(testFeeRecord);

        testFeeRecord.setAmount(new BigDecimal("2000.00"));
        FeeRecord updated = feeRecordService.updateFeeRecord(testFeeRecord);

        assertNotNull(updated);
        assertEquals(new BigDecimal("2000.00"), updated.getAmount());
        verify(feeRecordRepository, times(1)).save(any(FeeRecord.class));
    }

    @Test
    void getFeeRecord() {
        when(feeRecordRepository.findById(anyLong())).thenReturn(Optional.of(testFeeRecord));

        FeeRecord found = feeRecordService.getFeeRecord(1L);

        assertNotNull(found);
        assertEquals(testFeeRecord.getId(), found.getId());
        verify(feeRecordRepository, times(1)).findById(1L);
    }

    @Test
    void findFeeRecordsByCaseId() {
        when(feeRecordRepository.findByCaseId(anyLong())).thenReturn(Arrays.asList(testFeeRecord));

        List<FeeRecord> records = feeRecordService.findFeeRecordsByCaseId(1L);

        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
        assertEquals(testFeeRecord.getCaseId(), records.get(0).getCaseId());
        verify(feeRecordRepository, times(1)).findByCaseId(1L);
    }

    @Test
    void updatePaymentStatus() {
        when(feeRecordRepository.findById(anyLong())).thenReturn(Optional.of(testFeeRecord));
        when(feeRecordRepository.save(any(FeeRecord.class))).thenReturn(testFeeRecord);

        BigDecimal paidAmount = new BigDecimal("1000.00");
        FeeRecord updated = feeRecordService.updatePaymentStatus(1L, "PAID", paidAmount);

        assertNotNull(updated);
        assertEquals("PAID", updated.getFeeStatus());
        assertEquals(paidAmount, updated.getPaidAmount());
        assertNotNull(updated.getPaymentTime());
        verify(feeRecordRepository, times(1)).save(any(FeeRecord.class));
    }
} 