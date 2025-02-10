package com.lawfirm.model.contract.entity;

import com.lawfirm.model.contract.enums.ContractPriorityEnum;
import com.lawfirm.model.contract.enums.ContractStatusEnum;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    @Test
    void testContractBuilder() {
        // Given
        String contractNumber = "CT2024001";
        String contractName = "测试合同";
        BigDecimal amount = new BigDecimal("100000.00");
        LocalDateTime now = LocalDateTime.now();

        // When
        Contract contract = new Contract()
            .setContractNumber(contractNumber)
            .setContractName(contractName)
            .setContractType(ContractTypeEnum.LEGAL_SERVICE)
            .setContractStatus(ContractStatusEnum.DRAFT)
            .setPriority(ContractPriorityEnum.HIGH)
            .setAmount(amount)
            .setSignDate(now);

        // Then
        assertNotNull(contract);
        assertEquals(contractNumber, contract.getContractNumber());
        assertEquals(contractName, contract.getContractName());
        assertEquals(ContractTypeEnum.LEGAL_SERVICE, contract.getContractType());
        assertEquals(ContractStatusEnum.DRAFT, contract.getContractStatus());
        assertEquals(ContractPriorityEnum.HIGH, contract.getPriority());
        assertEquals(amount, contract.getAmount());
        assertEquals(now, contract.getSignDate());
    }

    @Test
    void testContractValidation() {
        // Given
        Contract contract = new Contract();

        // When & Then
        assertNull(contract.getContractNumber());
        assertNull(contract.getContractName());
        assertNull(contract.getContractType());
        assertNull(contract.getContractStatus());
    }

    @Test
    void testContractCalculations() {
        // Given
        Contract contract = new Contract()
            .setClauseCount(5)
            .setAttachmentCount(3)
            .setPartyCount(2);

        // Then
        assertEquals(5, contract.getClauseCount());
        assertEquals(3, contract.getAttachmentCount());
        assertEquals(2, contract.getPartyCount());
    }
} 