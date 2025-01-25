package com.lawfirm.contract.service;

import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.mapper.ContractMapper;
import com.lawfirm.contract.service.impl.ContractStatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractStatisticsServiceTest {

    @Mock
    private ContractMapper contractMapper;

    @InjectMocks
    private ContractStatisticsServiceImpl contractStatisticsService;

    private List<Contract> mockContracts;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        endTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);

        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setType(1);
        contract1.setStatus(1);
        contract1.setAmount(new BigDecimal("10000"));
        contract1.setLawyerId(1L);
        contract1.setDepartmentId(1L);
        contract1.setBranchId(1L);
        contract1.setCreateTime(LocalDateTime.of(2024, 1, 15, 10, 0));

        Contract contract2 = new Contract();
        contract2.setId(2L);
        contract2.setType(2);
        contract2.setStatus(2);
        contract2.setAmount(new BigDecimal("20000"));
        contract2.setLawyerId(2L);
        contract2.setDepartmentId(1L);
        contract2.setBranchId(1L);
        contract2.setCreateTime(LocalDateTime.of(2024, 2, 15, 10, 0));

        mockContracts = Arrays.asList(contract1, contract2);
    }

    @Test
    void countContracts() {
        when(contractMapper.selectCount(any())).thenReturn(2L);
        Long count = contractStatisticsService.countContracts(startTime, endTime, null, null);
        assertEquals(2L, count);
    }

    @Test
    void sumContractAmount() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        BigDecimal sum = contractStatisticsService.sumContractAmount(startTime, endTime, null, null);
        assertEquals(new BigDecimal("30000"), sum);
    }

    @Test
    void countContractsByType() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Integer, Long> typeCount = contractStatisticsService.countContractsByType(startTime, endTime);
        assertEquals(1L, typeCount.get(1));
        assertEquals(1L, typeCount.get(2));
    }

    @Test
    void countContractsByStatus() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Integer, Long> statusCount = contractStatisticsService.countContractsByStatus(startTime, endTime);
        assertEquals(1L, statusCount.get(1));
        assertEquals(1L, statusCount.get(2));
    }

    @Test
    void countContractsByLawyer() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Long, Long> lawyerCount = contractStatisticsService.countContractsByLawyer(startTime, endTime);
        assertEquals(1L, lawyerCount.get(1L));
        assertEquals(1L, lawyerCount.get(2L));
    }

    @Test
    void sumContractAmountByLawyer() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Long, BigDecimal> lawyerAmount = contractStatisticsService.sumContractAmountByLawyer(startTime, endTime);
        assertEquals(new BigDecimal("10000"), lawyerAmount.get(1L));
        assertEquals(new BigDecimal("20000"), lawyerAmount.get(2L));
    }

    @Test
    void countContractsByDepartment() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Long, Long> departmentCount = contractStatisticsService.countContractsByDepartment(startTime, endTime);
        assertEquals(2L, departmentCount.get(1L));
    }

    @Test
    void sumContractAmountByDepartment() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Long, BigDecimal> departmentAmount = contractStatisticsService.sumContractAmountByDepartment(startTime, endTime);
        assertEquals(new BigDecimal("30000"), departmentAmount.get(1L));
    }

    @Test
    void countContractsByMonth() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Integer, Long> monthCount = contractStatisticsService.countContractsByMonth(2024, null);
        assertEquals(1L, monthCount.get(1));
        assertEquals(1L, monthCount.get(2));
    }

    @Test
    void sumContractAmountByMonth() {
        when(contractMapper.selectList(any())).thenReturn(mockContracts);
        Map<Integer, BigDecimal> monthAmount = contractStatisticsService.sumContractAmountByMonth(2024, null);
        assertEquals(new BigDecimal("10000"), monthAmount.get(1));
        assertEquals(new BigDecimal("20000"), monthAmount.get(2));
    }
} 