package com.lawfirm.contract.service;

import com.lawfirm.contract.constant.ContractConstant.ClauseType;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.entity.ContractClause;
import com.lawfirm.contract.exception.ContractException;
import com.lawfirm.contract.service.impl.ContractClauseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractClauseServiceTest {

    @Mock
    private ContractService contractService;

    @InjectMocks
    private ContractClauseServiceImpl contractClauseService;

    private Contract contract;
    private ContractClause contractClause;

    @BeforeEach
    void setUp() {
        // 初始化合同
        contract = new Contract();
        contract.setId(1L);
        contract.setName("测试合同");

        // 初始化合同条款
        contractClause = new ContractClause();
        contractClause.setId(1L);
        contractClause.setContractId(1L);
        contractClause.setTitle("测试条款");
        contractClause.setContent("测试内容");
        contractClause.setType(ClauseType.BASIC);
        contractClause.setRequired(true);
        contractClause.setOrderNum(1);
    }

    @Test
    void createClause_Success() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试
        Long id = contractClauseService.createClause(contractClause);

        // 验证结果
        assertNotNull(id);
        verify(contractService, times(1)).getById(1L);
    }

    @Test
    void createClause_ContractNotFound() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(ContractException.class, () -> contractClauseService.createClause(contractClause));
    }

    @Test
    void createClauses_Success() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(contract);
        List<ContractClause> clauses = new ArrayList<>();
        clauses.add(contractClause);

        // 执行测试
        contractClauseService.createClauses(1L, clauses);

        // 验证结果
        verify(contractService, times(1)).getById(1L);
    }

    @Test
    void updateClause_Success() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(contract);
        when(contractClauseService.getById(1L)).thenReturn(contractClause);

        // 执行测试
        ContractClause updateClause = new ContractClause();
        updateClause.setId(1L);
        updateClause.setTitle("更新后的条款");
        contractClauseService.updateClause(updateClause);

        // 验证结果
        verify(contractService, times(1)).getById(1L);
        verify(contractClauseService, times(1)).updateById(any(ContractClause.class));
    }

    @Test
    void updateClause_ClauseNotFound() {
        // 准备测试数据
        when(contractClauseService.getById(1L)).thenReturn(null);

        // 执行测试并验证异常
        ContractClause updateClause = new ContractClause();
        updateClause.setId(1L);
        assertThrows(ContractException.class, () -> contractClauseService.updateClause(updateClause));
    }

    @Test
    void deleteClause_Success() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(contract);
        when(contractClauseService.getById(1L)).thenReturn(contractClause);

        // 执行测试
        contractClauseService.deleteClause(1L);

        // 验证结果
        verify(contractService, times(1)).getById(1L);
        verify(contractClauseService, times(1)).removeById(1L);
    }

    @Test
    void deleteClause_ClauseNotFound() {
        // 准备测试数据
        when(contractClauseService.getById(1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(ContractException.class, () -> contractClauseService.deleteClause(1L));
    }

    @Test
    void validateRequiredClauses_Success() {
        // 准备测试数据
        List<ContractClause> clauses = new ArrayList<>();
        clauses.add(contractClause);
        when(contractClauseService.list(any())).thenReturn(clauses);

        // 执行测试
        boolean result = contractClauseService.validateRequiredClauses(1L);

        // 验证结果
        assertTrue(result);
    }

    @Test
    void validateRequiredClauses_NoBasicClause() {
        // 准备测试数据
        contractClause.setType(ClauseType.SPECIAL);
        List<ContractClause> clauses = new ArrayList<>();
        clauses.add(contractClause);
        when(contractClauseService.list(any())).thenReturn(clauses);

        // 执行测试
        boolean result = contractClauseService.validateRequiredClauses(1L);

        // 验证结果
        assertFalse(result);
    }
} 