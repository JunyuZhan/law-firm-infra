package com.lawfirm.model.contract.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractEnumsTest {

    @Test
    void testContractStatusEnum() {
        // Given & When & Then
        assertEquals("DRAFT", ContractStatusEnum.DRAFT.getCode());
        assertEquals("草稿", ContractStatusEnum.DRAFT.getDescription());
        assertEquals("PENDING_REVIEW", ContractStatusEnum.PENDING_REVIEW.getCode());
        assertEquals("待审核", ContractStatusEnum.PENDING_REVIEW.getDescription());
    }

    @Test
    void testContractTypeEnum() {
        // Given & When & Then
        assertEquals("LEGAL_SERVICE", ContractTypeEnum.LEGAL_SERVICE.getCode());
        assertEquals("法律服务合同", ContractTypeEnum.LEGAL_SERVICE.getDescription());
        assertEquals("RETAINER", ContractTypeEnum.RETAINER.getCode());
        assertEquals("法律顾问合同", ContractTypeEnum.RETAINER.getDescription());
    }

    @Test
    void testContractPriorityEnum() {
        // Given & When & Then
        assertEquals("HIGH", ContractPriorityEnum.HIGH.getCode());
        assertEquals("高", ContractPriorityEnum.HIGH.getDescription());
        assertEquals("MEDIUM", ContractPriorityEnum.MEDIUM.getCode());
        assertEquals("中", ContractPriorityEnum.MEDIUM.getDescription());
    }

    @Test
    void testApprovalStatusEnum() {
        // Given & When & Then
        assertEquals("PENDING", ApprovalStatusEnum.PENDING.getCode());
        assertEquals("待审批", ApprovalStatusEnum.PENDING.getDescription());
        assertEquals("APPROVED", ApprovalStatusEnum.APPROVED.getCode());
        assertEquals("已通过", ApprovalStatusEnum.APPROVED.getDescription());
    }

    @Test
    void testPaymentTypeEnum() {
        // Given & When & Then
        assertEquals("LUMP_SUM", PaymentTypeEnum.LUMP_SUM.getCode());
        assertEquals("一次性付款", PaymentTypeEnum.LUMP_SUM.getDescription());
        assertEquals("INSTALLMENT", PaymentTypeEnum.INSTALLMENT.getCode());
        assertEquals("分期付款", PaymentTypeEnum.INSTALLMENT.getDescription());
    }
} 