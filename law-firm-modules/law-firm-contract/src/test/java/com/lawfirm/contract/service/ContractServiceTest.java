package com.lawfirm.contract.service;

import com.lawfirm.contract.constant.ContractConstant.ContractStatus;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.exception.ContractException;
import com.lawfirm.contract.service.impl.ContractServiceImpl;
import com.lawfirm.contract.util.ContractNoGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractNoGenerator contractNoGenerator;

    @Mock
    private ContractApprovalService contractApprovalService;

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract;

    @BeforeEach
    void setUp() {
        contract = new Contract();
        contract.setId(1L);
        contract.setName("测试合同");
        contract.setType(1);
        contract.setAmount(new BigDecimal("10000"));
        contract.setClientId(1L);
        contract.setClientName("测试客户");
        contract.setLawyerId(1L);
        contract.setLawyerName("测试律师");
        contract.setBranchId(1L);
        contract.setDepartmentId(1L);
        contract.setStatus(ContractStatus.DRAFT);
    }

    @Test
    void createContract_Success() {
        // 准备测试数据
        when(contractNoGenerator.generate(any())).thenReturn("202401CT0001");

        // 执行测试
        Long id = contractService.createContract(contract);

        // 验证结果
        assertNotNull(id);
        assertEquals("202401CT0001", contract.getContractNo());
        assertEquals(ContractStatus.DRAFT, contract.getStatus());
    }

    @Test
    void updateContract_Success() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试
        Contract updateContract = new Contract();
        updateContract.setId(1L);
        updateContract.setName("更新后的合同名称");
        contractService.updateContract(updateContract);

        // 验证结果
        verify(contractService, times(1)).updateById(any(Contract.class));
    }

    @Test
    void updateContract_NotFound() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(null);

        // 执行测试并验证异常
        Contract updateContract = new Contract();
        updateContract.setId(1L);
        assertThrows(ContractException.class, () -> contractService.updateContract(updateContract));
    }

    @Test
    void updateContract_InvalidStatus() {
        // 准备测试数据
        contract.setStatus(ContractStatus.APPROVING);
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试并验证异常
        Contract updateContract = new Contract();
        updateContract.setId(1L);
        assertThrows(ContractException.class, () -> contractService.updateContract(updateContract));
    }

    @Test
    void submitApproval_Success() {
        // 准备测试数据
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试
        contractService.submitApproval(1L);

        // 验证结果
        verify(contractService, times(1)).updateById(any(Contract.class));
        verify(contractApprovalService, times(1)).createApproval(eq(1L), eq(1), eq(1L), eq("部门负责人"));
    }

    @Test
    void submitApproval_InvalidStatus() {
        // 准备测试数据
        contract.setStatus(ContractStatus.APPROVING);
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试并验证异常
        assertThrows(ContractException.class, () -> contractService.submitApproval(1L));
    }

    @Test
    void terminateContract_Success() {
        // 准备测试数据
        contract.setStatus(ContractStatus.EFFECTIVE);
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试
        contractService.terminateContract(1L, "测试终止原因");

        // 验证结果
        verify(contractService, times(1)).updateById(any(Contract.class));
    }

    @Test
    void terminateContract_InvalidStatus() {
        // 准备测试数据
        contract.setStatus(ContractStatus.DRAFT);
        when(contractService.getById(1L)).thenReturn(contract);

        // 执行测试并验证异常
        assertThrows(ContractException.class, () -> contractService.terminateContract(1L, "测试终止原因"));
    }
} 