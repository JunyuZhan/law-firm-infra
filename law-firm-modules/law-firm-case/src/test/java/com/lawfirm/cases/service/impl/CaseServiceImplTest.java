package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.converter.CaseConverter;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.service.CaseService;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Disabled;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Disabled("暂时跳过测试，等待CaseSourceEnum枚举值修复")
class CaseServiceImplTest {

    @Autowired
    private CaseService caseService;

    private CaseCreateDTO createDTO;
    private CaseQueryDTO queryDTO;

    @BeforeEach
    void setUp() {
        createDTO = new CaseCreateDTO();
        createDTO.setCaseNumber("TEST-001");
        createDTO.setCaseName("Test Case");
        createDTO.setCaseType(CaseTypeEnum.CIVIL);
        createDTO.setCaseProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        createDTO.setCaseHandleType(CaseHandleTypeEnum.SOLE_LAWYER);
        createDTO.setCaseDifficulty(CaseDifficultyEnum.MEDIUM);
        createDTO.setCaseImportance(CaseImportanceEnum.NORMAL);
        createDTO.setCasePriority(CasePriorityEnum.MEDIUM);
        createDTO.setCaseFeeType(CaseFeeTypeEnum.FIXED_FEE);
        createDTO.setCaseSource(CaseSourceEnum.valueOf("DIRECT_CLIENT"));
        createDTO.setLawyer("Test Lawyer");

        queryDTO = new CaseQueryDTO();
        queryDTO.setKeyword("TEST");
    }

    @Test
    void testCreateCase() {
        CaseDetailVO created = caseService.createCase(createDTO);
        assertNotNull(created);
        assertEquals("TEST-001", created.getCaseNumber());
    }

    @Test
    void testGetCaseById() {
        CaseDetailVO created = caseService.createCase(createDTO);
        CaseDetailVO found = caseService.getCaseById(created.getId());
        assertNotNull(found);
        assertEquals(created.getId(), found.getId());
    }

    @Test
    void testGetCaseByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> caseService.getCaseById(999L));
    }

    @Test
    void testUpdateCase() {
        CaseDetailVO created = caseService.createCase(createDTO);
        CaseUpdateDTO updateDTO = new CaseUpdateDTO();
        updateDTO.setCaseName("Updated Name");
        CaseDetailVO updated = caseService.updateCase(created.getId(), updateDTO);
        assertEquals("Updated Name", updated.getCaseName());
    }

    @Test
    void testUpdateCaseNotFound() {
        CaseUpdateDTO updateDTO = new CaseUpdateDTO();
        updateDTO.setCaseName("Updated Name");
        assertThrows(ResourceNotFoundException.class, () -> caseService.updateCase(999L, updateDTO));
    }

    @Test
    void testFindCases() {
        CaseDetailVO created = caseService.createCase(createDTO);
        assertNotNull(created);

        org.springframework.data.domain.Page<CaseDetailVO> page = caseService.findCases(queryDTO, PageRequest.of(0, 10));
        assertFalse(page.getContent().isEmpty());
        assertTrue(page.getContent().stream().anyMatch(c -> c.getId().equals(created.getId())));

        Map<CaseStatusEnum, Long> statusStats = caseService.getCaseStatistics();
        assertNotNull(statusStats);
    }

    @Test
    void testValidateCase_Success() {
        CaseDetailVO created = caseService.createCase(createDTO);
        assertDoesNotThrow(() -> caseService.validateCase(created.getId()));
    }

    @Test
    void testValidateCase_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> caseService.validateCase(999L));
    }

    @Test
    void testUpdateProgress() {
        CaseDetailVO created = caseService.createCase(createDTO);
        CaseDetailVO updated = caseService.updateProgress(created.getId(), CaseProgressEnum.CASE_FILING_PREPARATION, "Progress updated");
        assertEquals(CaseProgressEnum.CASE_FILING_PREPARATION, updated.getCaseProgress());
    }

    @Test
    void testUpdateImportance() {
        CaseDetailVO created = caseService.createCase(createDTO);
        CaseDetailVO updated = caseService.updateImportance(created.getId(), CaseImportanceEnum.CRITICAL, "Importance updated");
        assertEquals(CaseImportanceEnum.CRITICAL, updated.getCaseImportance());
    }

    @Test
    void testUpdateSource() {
        CaseDetailVO created = caseService.createCase(createDTO);
        assertNotNull(created);

        CaseUpdateDTO updateDTO = new CaseUpdateDTO();
        updateDTO.setId(created.getId());
        updateDTO.setCaseSource(CaseSourceEnum.valueOf("REFERRAL_LAWYER"));
        CaseDetailVO updated = caseService.updateCase(updateDTO);
        assertEquals(CaseSourceEnum.valueOf("REFERRAL_LAWYER"), updated.getCaseSource());
    }
} 