package com.lawfirm.model.contract.dto;

import com.lawfirm.model.contract.dto.request.ContractCreateRequest;
import com.lawfirm.model.contract.dto.request.ContractUpdateRequest;
import com.lawfirm.model.contract.dto.response.ContractDetailResponse;
import com.lawfirm.model.contract.enums.ContractTypeEnum;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContractDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testContractCreateRequestValidation() {
        // Given
        ContractCreateRequest request = new ContractCreateRequest();

        // When
        var violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("contractName")));
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("contractType")));
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("amount")));
    }

    @Test
    void testContractCreateRequestValid() {
        // Given
        ContractCreateRequest request = new ContractCreateRequest();
        request.setContractName("测试合同");
        request.setContractType(ContractTypeEnum.LEGAL_SERVICE);
        request.setAmount(new BigDecimal("100000.00"));

        // When
        var violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testContractUpdateRequestValidation() {
        // Given
        ContractUpdateRequest request = new ContractUpdateRequest();

        // When
        var violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("id")));
    }

    @Test
    void testContractDetailResponse() {
        // Given
        ContractDetailResponse response = new ContractDetailResponse();
        response.setId(1L);
        response.setContractName("测试合同");
        response.setContractType(ContractTypeEnum.LEGAL_SERVICE);
        response.setAmount(new BigDecimal("100000.00"));

        // Then
        assertEquals(1L, response.getId());
        assertEquals("测试合同", response.getContractName());
        assertEquals(ContractTypeEnum.LEGAL_SERVICE, response.getContractType());
        assertEquals(new BigDecimal("100000.00"), response.getAmount());
    }
} 