package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.converter.CaseConverter;
import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.model.dto.CaseCreateDTO;
import com.lawfirm.cases.model.dto.CaseQueryDTO;
import com.lawfirm.cases.model.dto.CaseUpdateDTO;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaseServiceImplTest {

    @Mock
    private CaseMapper caseMapper;

    @Mock
    private CaseConverter caseConverter;

    @InjectMocks
    private CaseServiceImpl caseService;

    private Case testCase;
    private CaseCreateDTO createDTO;
    private CaseUpdateDTO updateDTO;
    private CaseDetailVO detailVO;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testCase = new Case();
        testCase.setId(1L);
        testCase.setCaseNumber("TEST-001");
        testCase.setCaseName("测试案件");
        testCase.setDescription("测试案件描述");
        testCase.setCaseType(CaseTypeEnum.CIVIL);
        testCase.setCaseStatus(CaseStatusEnum.DRAFT);
        testCase.setProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        testCase.setHandleType(CaseHandleTypeEnum.SOLE_LAWYER);
        testCase.setDifficulty(CaseDifficultyEnum.MEDIUM);
        testCase.setImportance(CaseImportanceEnum.NORMAL);
        testCase.setPriority(CasePriorityEnum.MEDIUM);
        testCase.setFeeType(CaseFeeTypeEnum.FIXED_FEE);
        testCase.setSource(CaseSourceEnum.DIRECT_CLIENT);
        testCase.setLawyer("张律师");
        testCase.setClientId(1L);
        testCase.setLawFirmId(1L);
        testCase.setFee(new BigDecimal("10000"));
        testCase.setFilingTime(LocalDateTime.now());
        testCase.setStatusEnum(StatusEnum.ENABLED);

        createDTO = new CaseCreateDTO();
        createDTO.setCaseNumber("TEST-001");
        createDTO.setCaseName("测试案件");
        createDTO.setDescription("测试案件描述");
        createDTO.setCaseType(CaseTypeEnum.CIVIL);
        createDTO.setProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        createDTO.setHandleType(CaseHandleTypeEnum.SOLE_LAWYER);
        createDTO.setFeeType(CaseFeeTypeEnum.FIXED_FEE);
        createDTO.setLawyer("张律师");
        createDTO.setClientId(1L);
        createDTO.setLawFirmId(1L);
        createDTO.setFee(new BigDecimal("10000"));

        updateDTO = new CaseUpdateDTO();
        updateDTO.setCaseName("更新后的测试案件");
        updateDTO.setDescription("更新后的测试案件描述");
        updateDTO.setCaseType(CaseTypeEnum.CIVIL);
        updateDTO.setProgress(CaseProgressEnum.CASE_FILING_SUBMITTED);
        updateDTO.setHandleType(CaseHandleTypeEnum.TEAM_LEADER);
        updateDTO.setFeeType(CaseFeeTypeEnum.HOURLY_RATE);
        updateDTO.setLawyer("李律师");
        updateDTO.setFee(new BigDecimal("20000"));

        detailVO = new CaseDetailVO();
        detailVO.setId(1L);
        detailVO.setCaseNumber("TEST-001");
        detailVO.setCaseName("测试案件");
        detailVO.setDescription("测试案件描述");
        detailVO.setCaseType(CaseTypeEnum.CIVIL);
        detailVO.setCaseStatus(CaseStatusEnum.DRAFT);
        detailVO.setProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        detailVO.setHandleType(CaseHandleTypeEnum.SOLE_LAWYER);
        detailVO.setDifficulty(CaseDifficultyEnum.MEDIUM);
        detailVO.setImportance(CaseImportanceEnum.NORMAL);
        detailVO.setPriority(CasePriorityEnum.MEDIUM);
        detailVO.setFeeType(CaseFeeTypeEnum.FIXED_FEE);
        detailVO.setSource(CaseSourceEnum.DIRECT_CLIENT);
        detailVO.setLawyer("张律师");
        detailVO.setClientId(1L);
        detailVO.setLawFirmId(1L);
        detailVO.setFee(new BigDecimal("10000"));
        detailVO.setFilingTime(testCase.getFilingTime());
    }

    @Test
    void createCase_Success() {
        // 准备测试数据
        when(caseConverter.toEntity(any(CaseCreateDTO.class))).thenReturn(testCase);
        when(caseMapper.insert(any(Case.class))).thenReturn(1);
        when(caseConverter.toDetailVO(any(Case.class))).thenReturn(detailVO);

        // 执行测试
        CaseDetailVO result = caseService.createCase(createDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(detailVO.getCaseNumber(), result.getCaseNumber());
        assertEquals(detailVO.getCaseName(), result.getCaseName());
        assertEquals(CaseStatusEnum.DRAFT, result.getCaseStatus());
        
        // 验证方法调用
        verify(caseConverter).toEntity(createDTO);
        verify(caseMapper).insert(testCase);
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void updateCase_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);
        when(caseConverter.toDetailVO(any(Case.class))).thenReturn(detailVO);

        // 执行测试
        CaseDetailVO result = caseService.updateCase(1L, updateDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(detailVO.getId(), result.getId());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void updateCase_NotFound() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(ResourceNotFoundException.class, () -> caseService.updateCase(1L, updateDTO));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).updateById(any());
    }

    @Test
    void deleteCase_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.deleteById(1L)).thenReturn(1);

        // 执行测试
        caseService.deleteCase(1L);

        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).deleteById(1L);
    }

    @Test
    void deleteCase_NotFound() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(ResourceNotFoundException.class, () -> caseService.deleteCase(1L));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).deleteById(any());
    }

    @Test
    void getCaseById_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseConverter.toDetailVO(testCase)).thenReturn(detailVO);

        // 执行测试
        CaseDetailVO result = caseService.getCaseById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(detailVO.getId(), result.getId());
        assertEquals(detailVO.getCaseNumber(), result.getCaseNumber());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void getCaseById_NotFound() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(ResourceNotFoundException.class, () -> caseService.getCaseById(1L));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseConverter, never()).toDetailVO(any());
    }

    @Test
    void findCases_Success() {
        // 准备测试数据
        CaseQueryDTO queryDTO = new CaseQueryDTO();
        queryDTO.setCaseNumber("TEST");
        queryDTO.setCaseType(CaseTypeEnum.CIVIL);

        Pageable pageable = PageRequest.of(0, 10);
        List<Case> cases = Arrays.asList(testCase);
        Page<Case> page = new Page<>(1, 10, 1);
        page.setRecords(cases);

        when(caseMapper.selectPage(any(), any())).thenReturn(page);
        when(caseConverter.toDetailVO(testCase)).thenReturn(detailVO);

        // 执行测试
        org.springframework.data.domain.Page<CaseDetailVO> result = caseService.findCases(queryDTO, pageable);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(detailVO, result.getContent().get(0));
        
        // 验证方法调用
        verify(caseMapper).selectPage(any(), any());
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void updateProgress_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);
        when(caseConverter.toDetailVO(any(Case.class))).thenReturn(detailVO);

        // 执行测试
        CaseDetailVO result = caseService.updateProgress(1L, CaseProgressEnum.CASE_FILING_SUBMITTED, "操作人");

        // 验证结果
        assertNotNull(result);
        assertEquals(detailVO.getId(), result.getId());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void getCurrentProgress_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试
        CaseProgressEnum result = caseService.getCurrentProgress(1L);

        // 验证结果
        assertEquals(testCase.getProgress(), result);
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
    }

    @Test
    void getProgressStatistics_Success() {
        // 准备测试数据
        List<Map<String, Object>> statistics = Arrays.asList(
            Map.of("progress", CaseProgressEnum.CASE_FILING_PREPARATION, "count", 5L),
            Map.of("progress", CaseProgressEnum.CASE_FILING_SUBMITTED, "count", 3L)
        );
        when(caseMapper.selectMaps(any())).thenReturn(statistics);

        // 执行测试
        Map<CaseProgressEnum, Long> result = caseService.getProgressStatistics();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5L, result.get(CaseProgressEnum.CASE_FILING_PREPARATION));
        assertEquals(3L, result.get(CaseProgressEnum.CASE_FILING_SUBMITTED));
        
        // 验证方法调用
        verify(caseMapper).selectMaps(any());
    }

    @Test
    void getCaseByCaseNumber_Success() {
        // 准备测试数据
        when(caseMapper.selectOne(any())).thenReturn(testCase);
        when(caseConverter.toDetailVO(testCase)).thenReturn(detailVO);

        // 执行测试
        CaseDetailVO result = caseService.getCaseByCaseNumber("TEST-001");

        // 验证结果
        assertNotNull(result);
        assertEquals(detailVO.getCaseNumber(), result.getCaseNumber());
        
        // 验证方法调用
        verify(caseMapper).selectOne(any());
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void getCaseByCaseNumber_NotFound() {
        // 准备测试数据
        when(caseMapper.selectOne(any())).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(ResourceNotFoundException.class, () -> caseService.getCaseByCaseNumber("NOT-EXIST"));
        
        // 验证方法调用
        verify(caseMapper).selectOne(any());
        verify(caseConverter, never()).toDetailVO(any());
    }

    @Test
    void createCase_WithNullCaseNumber() {
        // 准备测试数据
        createDTO.setCaseNumber(null);

        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> caseService.createCase(createDTO));
    }

    @Test
    void createCase_WithExistingCaseNumber() {
        // 准备测试数据
        when(caseMapper.exists(any())).thenReturn(true);

        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> caseService.createCase(createDTO));
    }

    @Test
    void updateCase_WithInvalidStatus() {
        // 准备测试数据
        testCase.setCaseStatus(CaseStatusEnum.COMPLETED);
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试并验证异常
        assertThrows(IllegalStateException.class, () -> caseService.updateCase(1L, updateDTO));
    }

    @Test
    void findCasesByLawyer_Success() {
        // 准备测试数据
        List<Case> cases = Arrays.asList(testCase);
        when(caseMapper.selectList(any())).thenReturn(cases);
        when(caseConverter.toDetailVO(any(Case.class))).thenReturn(detailVO);

        // 执行测试
        List<CaseDetailVO> result = caseService.findCasesByLawyer("张律师");

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(detailVO.getLawyer(), result.get(0).getLawyer());
        
        // 验证方法调用
        verify(caseMapper).selectList(any());
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void findCasesByClient_Success() {
        // 准备测试数据
        List<Case> cases = Arrays.asList(testCase);
        when(caseMapper.selectList(any())).thenReturn(cases);
        when(caseConverter.toDetailVO(any(Case.class))).thenReturn(detailVO);

        // 执行测试
        List<CaseDetailVO> result = caseService.findCasesByClient(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(detailVO.getClientId(), result.get(0).getClientId());
        
        // 验证方法调用
        verify(caseMapper).selectList(any());
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void findCasesByStatus_Success() {
        // 准备测试数据
        List<Case> cases = Arrays.asList(testCase);
        when(caseMapper.selectList(any())).thenReturn(cases);
        when(caseConverter.toDetailVO(any(Case.class))).thenReturn(detailVO);

        // 执行测试
        List<CaseDetailVO> result = caseService.findCasesByStatus(CaseStatusEnum.DRAFT);

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(detailVO.getCaseStatus(), result.get(0).getCaseStatus());
        
        // 验证方法调用
        verify(caseMapper).selectList(any());
        verify(caseConverter).toDetailVO(testCase);
    }

    @Test
    void findCases_WithEmptyResult() {
        // 准备测试数据
        CaseQueryDTO queryDTO = new CaseQueryDTO();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Case> emptyPage = new Page<>(1, 10, 0);
        emptyPage.setRecords(Arrays.asList());

        when(caseMapper.selectPage(any(), any())).thenReturn(emptyPage);

        // 执行测试
        org.springframework.data.domain.Page<CaseDetailVO> result = caseService.findCases(queryDTO, pageable);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void findCases_WithInvalidPageSize() {
        // 准备测试数据
        CaseQueryDTO queryDTO = new CaseQueryDTO();
        Pageable pageable = PageRequest.of(0, -1);

        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> caseService.findCases(queryDTO, pageable));
    }

    @Test
    void validateCase_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);

        // 执行测试
        caseService.validateCase(1L);

        // 验证结果
        assertEquals(CaseStatusEnum.IN_PROGRESS, testCase.getCaseStatus());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
    }

    @Test
    void archiveCase_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);

        // 执行测试
        caseService.archiveCase(1L, "操作人");

        // 验证结果
        assertEquals(CaseStatusEnum.ARCHIVED, testCase.getCaseStatus());
        assertNotNull(testCase.getClosingTime());
        assertEquals("操作人", testCase.getOperator());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
    }

    @Test
    void reopenCase_Success() {
        // 准备测试数据
        testCase.setCaseStatus(CaseStatusEnum.ARCHIVED);
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);

        // 执行测试
        caseService.reopenCase(1L, "操作人", "重新开启原因");

        // 验证结果
        assertEquals(CaseStatusEnum.IN_PROGRESS, testCase.getCaseStatus());
        assertNull(testCase.getClosingTime());
        assertEquals("操作人", testCase.getOperator());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
    }

    @Test
    void suspendCase_Success() {
        // 准备测试数据
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);

        // 执行测试
        caseService.suspendCase(1L, "操作人", "暂停原因");

        // 验证结果
        assertEquals(CaseStatusEnum.SUSPENDED, testCase.getCaseStatus());
        assertEquals("操作人", testCase.getOperator());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
    }

    @Test
    void resumeCase_Success() {
        // 准备测试数据
        testCase.setCaseStatus(CaseStatusEnum.SUSPENDED);
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);

        // 执行测试
        caseService.resumeCase(1L, "操作人");

        // 验证结果
        assertEquals(CaseStatusEnum.IN_PROGRESS, testCase.getCaseStatus());
        assertEquals("操作人", testCase.getOperator());
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(testCase);
    }

    @Test
    void countCasesByStatus_Success() {
        // 准备测试数据
        when(caseMapper.selectCount(any())).thenReturn(5L);

        // 执行测试
        long count = caseService.countCasesByStatus(CaseStatusEnum.IN_PROGRESS);

        // 验证结果
        assertEquals(5L, count);
        
        // 验证方法调用
        verify(caseMapper).selectCount(any());
    }

    @Test
    void countCasesByDateRange_Success() {
        // 准备测试数据
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        when(caseMapper.selectCount(any())).thenReturn(10L);

        // 执行测试
        long count = caseService.countCasesByDateRange(startDate, endDate);

        // 验证结果
        assertEquals(10L, count);
        
        // 验证方法调用
        verify(caseMapper).selectCount(any());
    }

    @Test
    void countCasesByDateRange_InvalidDateRange() {
        // 准备测试数据
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().minusDays(7);

        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, 
            () -> caseService.countCasesByDateRange(startDate, endDate));
    }

    @Test
    void updateProgress_InvalidProgressOrder() {
        // 准备测试数据
        testCase.setProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试并验证异常
        assertThrows(IllegalStateException.class, 
            () -> caseService.updateProgress(1L, CaseProgressEnum.CASE_CLOSED, "操作人"));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).updateById(any());
    }

    @Test
    void updateProgress_SameProgress() {
        // 准备测试数据
        testCase.setProgress(CaseProgressEnum.CASE_FILING_PREPARATION);
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试并验证异常
        assertThrows(IllegalStateException.class, 
            () -> caseService.updateProgress(1L, CaseProgressEnum.CASE_FILING_PREPARATION, "操作人"));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).updateById(any());
    }

    @Test
    void reopenCase_FromNonArchivedStatus() {
        // 准备测试数据
        testCase.setCaseStatus(CaseStatusEnum.IN_PROGRESS);
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试并验证异常
        assertThrows(IllegalStateException.class, 
            () -> caseService.reopenCase(1L, "操作人", "重新开启原因"));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).updateById(any());
    }

    @Test
    void resumeCase_FromNonSuspendedStatus() {
        // 准备测试数据
        testCase.setCaseStatus(CaseStatusEnum.IN_PROGRESS);
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试并验证异常
        assertThrows(IllegalStateException.class, 
            () -> caseService.resumeCase(1L, "操作人"));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).updateById(any());
    }

    @Test
    void updateProgress_CompletedCase() {
        // 准备测试数据
        testCase.setCaseStatus(CaseStatusEnum.COMPLETED);
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        // 执行测试并验证异常
        assertThrows(IllegalStateException.class, 
            () -> caseService.updateProgress(1L, CaseProgressEnum.CASE_FILING_SUBMITTED, "操作人"));
        
        // 验证方法调用
        verify(caseMapper).selectById(1L);
        verify(caseMapper, never()).updateById(any());
    }

    @Test
    void findCases_WithInvalidPageNumber() {
        // 准备测试数据
        CaseQueryDTO queryDTO = new CaseQueryDTO();
        Pageable pageable = PageRequest.of(999999, 10);
        Page<Case> emptyPage = new Page<>(999999, 10, 0);
        emptyPage.setRecords(Collections.emptyList());

        when(caseMapper.selectPage(any(), any())).thenReturn(emptyPage);

        // 执行测试
        org.springframework.data.domain.Page<CaseDetailVO> result = caseService.findCases(queryDTO, pageable);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void findCases_WithSorting() {
        // 准备测试数据
        CaseQueryDTO queryDTO = new CaseQueryDTO();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(0, 10, sort);
        List<Case> cases = Arrays.asList(testCase);
        Page<Case> page = new Page<>(1, 10, 1);
        page.setRecords(cases);

        when(caseMapper.selectPage(any(), any())).thenReturn(page);
        when(caseConverter.toDetailVO(testCase)).thenReturn(detailVO);

        // 执行测试
        org.springframework.data.domain.Page<CaseDetailVO> result = caseService.findCases(queryDTO, pageable);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(detailVO, result.getContent().get(0));
        
        // 验证方法调用
        verify(caseMapper).selectPage(any(), any());
        verify(caseConverter).toDetailVO(testCase);
    }
} 