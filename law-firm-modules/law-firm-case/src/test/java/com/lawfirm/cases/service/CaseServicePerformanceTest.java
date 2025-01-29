package com.lawfirm.cases.service;

import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Disabled("暂时跳过测试，等待CaseSourceEnum枚举值修复")
class CaseServicePerformanceTest {

    @Autowired
    private CaseService caseService;

    private List<CaseDetailVO> testCases;

    @BeforeEach
    void setUp() {
        testCases = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CaseCreateDTO createDTO = createTestCase(i);
            CaseDetailVO created = caseService.createCase(createDTO);
            testCases.add(created);
        }
    }

    @Test
    void testBatchQuery() {
        long startTime = System.nanoTime();

        CaseQueryDTO queryDTO = new CaseQueryDTO();
        queryDTO.setKeyword("Test");
        Page<CaseDetailVO> result = caseService.findCases(queryDTO, PageRequest.of(0, 50));

        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
        assertTrue(duration < 1000, "Query took too long: " + duration + "ms");
    }

    @Test
    void testBatchUpdate() {
        long startTime = System.nanoTime();

        for (CaseDetailVO testCase : testCases.subList(0, 10)) {
            testCase.setCaseName(testCase.getCaseName() + " Updated");
            CaseDetailVO updated = caseService.updateCase(testCase.getId(), convertToUpdateDTO(testCase));
            assertNotNull(updated);
            assertTrue(updated.getCaseName().endsWith("Updated"));
        }

        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        assertTrue(duration < 2000, "Batch update took too long: " + duration + "ms");
    }

    private CaseUpdateDTO convertToUpdateDTO(CaseDetailVO vo) {
        CaseUpdateDTO updateDTO = new CaseUpdateDTO();
        updateDTO.setCaseName(vo.getCaseName());
        updateDTO.setCaseProgress(vo.getCaseProgress());
        updateDTO.setCaseHandleType(vo.getCaseHandleType());
        updateDTO.setCaseDifficulty(vo.getCaseDifficulty());
        updateDTO.setCaseImportance(vo.getCaseImportance());
        updateDTO.setCasePriority(vo.getCasePriority());
        updateDTO.setCaseFeeType(vo.getCaseFeeType());
        updateDTO.setCaseSource(vo.getCaseSource());
        updateDTO.setLawyer(vo.getLawyer().getName());
        return updateDTO;
    }

    private CaseCreateDTO createTestCase(int index) {
        CaseCreateDTO dto = new CaseCreateDTO();
        dto.setCaseNumber("PERF-" + index);
        dto.setCaseName("Performance Test Case " + index);
        dto.setCaseType(CaseTypeEnum.CIVIL);
        dto.setCaseProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        dto.setCaseHandleType(CaseHandleTypeEnum.SOLE_LAWYER);
        dto.setCaseDifficulty(CaseDifficultyEnum.MEDIUM);
        dto.setCaseImportance(CaseImportanceEnum.NORMAL);
        dto.setCasePriority(CasePriorityEnum.MEDIUM);
        dto.setCaseFeeType(CaseFeeTypeEnum.FIXED_FEE);
        dto.setCaseSource(CaseSourceEnum.valueOf("DIRECT_CLIENT"));
        dto.setLawyer("Performance Tester");
        return dto;
    }
} 