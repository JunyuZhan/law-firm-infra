package com.lawfirm.finance.service.impl;

import com.lawfirm.finance.dto.request.FeeRecordAddRequest;
import com.lawfirm.finance.dto.request.FeeRecordUpdateRequest;
import com.lawfirm.finance.entity.FeeRecord;
import com.lawfirm.finance.mapper.FeeRecordMapper;
import com.lawfirm.finance.repository.FeeRecordRepository;
import com.lawfirm.finance.dto.response.FeeRecordResponse;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FeeRecordServiceImplTest {

    @Mock
    private FeeRecordMapper feeRecordMapper;

    @Mock
    private FeeRecordRepository feeRecordRepository;

    @InjectMocks
    private FeeRecordServiceImpl feeRecordService;

    private FeeRecordAddRequest addRequest;
    private FeeRecord feeRecord;
    private FeeRecordUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        addRequest = new FeeRecordAddRequest();
        addRequest.setAmount(new BigDecimal("1000.00"));
        addRequest.setFeeType("CASE");
        addRequest.setClientId(1L);
        addRequest.setLawFirmId(1L);
        addRequest.setDescription("测试收费");

        feeRecord = new FeeRecord();
        feeRecord.setId(1L);
        feeRecord.setAmount(new BigDecimal("1000.00"));
        feeRecord.setFeeType("CASE");
        feeRecord.setClientId(1L);
        feeRecord.setLawFirmId(1L);
        feeRecord.setDescription("测试收费");
        feeRecord.setFeeStatus("UNPAID");
        feeRecord.setPaidAmount(BigDecimal.ZERO);

        updateRequest = new FeeRecordUpdateRequest();
        updateRequest.setId(1L);
        updateRequest.setAmount(new BigDecimal("2000.00"));
        updateRequest.setDescription("更新的测试收费");
    }

    @Test
    void testAddFeeRecord() {
        // 设置模拟行为
        when(feeRecordService.save(any(FeeRecord.class))).thenReturn(true);

        // 执行测试
        Long result = feeRecordService.addFeeRecord(addRequest);

        // 验证结果
        assertNotNull(result);
        verify(feeRecordService, times(1)).save(any(FeeRecord.class));
    }

    @Test
    void testUpdateFeeRecord() {
        // 设置模拟行为
        when(feeRecordService.getById(1L)).thenReturn(feeRecord);
        when(feeRecordService.updateById(any(FeeRecord.class))).thenReturn(true);

        // 执行测试
        assertDoesNotThrow(() -> feeRecordService.updateFeeRecord(updateRequest));

        // 验证结果
        verify(feeRecordService, times(1)).getById(1L);
        verify(feeRecordService, times(1)).updateById(any(FeeRecord.class));
    }

    @Test
    void testDeleteFeeRecord() {
        // 设置模拟行为
        when(feeRecordService.getById(1L)).thenReturn(feeRecord);
        when(feeRecordService.removeById(1L)).thenReturn(true);

        // 执行测试
        assertDoesNotThrow(() -> feeRecordService.deleteFeeRecord(1L));

        // 验证结果
        verify(feeRecordService, times(1)).getById(1L);
        verify(feeRecordService, times(1)).removeById(1L);
    }

    @Test
    void testGetFeeRecordById() {
        // 设置模拟行为
        when(feeRecordService.getById(1L)).thenReturn(feeRecord);

        // 执行测试
        FeeRecordResponse result = feeRecordService.getFeeRecordById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(feeRecord.getId(), result.getId());
        assertEquals(feeRecord.getAmount(), result.getAmount());
        verify(feeRecordService, times(1)).getById(1L);
    }

    @Test
    void testUpdateFeeRecordWithInvalidId() {
        // 设置模拟行为
        when(feeRecordService.getById(1L)).thenReturn(null);

        // 执行测试和验证结果
        assertThrows(BusinessException.class, () -> feeRecordService.updateFeeRecord(updateRequest));
        verify(feeRecordService, times(1)).getById(1L);
        verify(feeRecordService, never()).updateById(any(FeeRecord.class));
    }

    @Test
    void testPayFee() {
        // 设置模拟行为
        when(feeRecordService.getById(1L)).thenReturn(feeRecord);
        when(feeRecordRepository.save(any(FeeRecord.class))).thenReturn(feeRecord);

        // 执行测试
        assertDoesNotThrow(() -> feeRecordService.payFee(1L, new BigDecimal("500.00")));

        // 验证结果
        verify(feeRecordService, times(1)).getById(1L);
        verify(feeRecordRepository, times(1)).save(any(FeeRecord.class));
    }

    @Test
    void testCalculateTotalAmount() {
        // 设置模拟行为
        when(feeRecordRepository.findByLawFirmId(1L)).thenReturn(List.of(feeRecord));

        // 执行测试
        BigDecimal result = feeRecordService.calculateTotalAmount(1L);

        // 验证结果
        assertEquals(new BigDecimal("1000.00"), result);
        verify(feeRecordRepository, times(1)).findByLawFirmId(1L);
    }
} 