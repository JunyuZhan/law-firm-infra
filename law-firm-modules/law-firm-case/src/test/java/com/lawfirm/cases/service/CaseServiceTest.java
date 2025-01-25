package com.lawfirm.cases.service;

import com.lawfirm.cases.mapper.CaseMapper;
import com.lawfirm.cases.mapper.CaseStatusLogMapper;
import com.lawfirm.cases.service.impl.CaseServiceImpl;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.entity.CaseStatusLog;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.query.CaseQuery;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaseServiceTest {

    @Mock
    private CaseMapper caseMapper;

    @Mock
    private CaseStatusLogMapper caseStatusLogMapper;

    @InjectMocks
    private CaseServiceImpl caseService;

    private Case testCase;
    private CaseStatusLog testStatusLog;

    @BeforeEach
    void setUp() {
        testCase = new Case();
        testCase.setId(1L);
        testCase.setCaseNumber("TEST-001");
        testCase.setCaseName("测试案件");
        testCase.setCaseStatus(CaseStatusEnum.DRAFT);

        testStatusLog = new CaseStatusLog();
        testStatusLog.setCaseId(1L);
        testStatusLog.setFromStatus(CaseStatusEnum.DRAFT);
        testStatusLog.setToStatus(CaseStatusEnum.PENDING);
        testStatusLog.setOperator("admin");
    }

    @Test
    void validateCase() {
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);
        when(caseStatusLogMapper.insert(any(CaseStatusLog.class))).thenReturn(1);

        caseService.validateCase(1L, "admin");

        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(any(Case.class));
        verify(caseStatusLogMapper).insert(any(CaseStatusLog.class));
    }

    @Test
    void archiveCase() {
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);
        when(caseStatusLogMapper.insert(any(CaseStatusLog.class))).thenReturn(1);

        caseService.archiveCase(1L, "admin");

        verify(caseMapper).selectById(1L);
        verify(caseMapper).updateById(any(Case.class));
        verify(caseStatusLogMapper).insert(any(CaseStatusLog.class));
    }

    @Test
    void getStatusHistory() {
        when(caseStatusLogMapper.selectList(any())).thenReturn(Arrays.asList(testStatusLog));

        List<CaseStatusVO> history = caseService.getStatusHistory(1L);

        assertNotNull(history);
        assertFalse(history.isEmpty());
        assertEquals(1, history.size());
        assertEquals(testStatusLog.getToStatus(), history.get(0).getStatus());
    }

    @Test
    void getCurrentStatus() {
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        CaseStatusVO status = caseService.getCurrentStatus(1L);

        assertNotNull(status);
        assertEquals(testCase.getCaseStatus(), status.getStatus());
    }

    @Test
    void getAvailableStatus() {
        when(caseMapper.selectById(1L)).thenReturn(testCase);

        List<String> availableStatus = caseService.getAvailableStatus(1L);

        assertNotNull(availableStatus);
        assertFalse(availableStatus.isEmpty());
        assertTrue(availableStatus.contains(CaseStatusEnum.PENDING.name()));
    }

    @Test
    void updateStatus() {
        testCase.setCaseStatus(CaseStatusEnum.DRAFT);
        when(caseMapper.selectById(1L)).thenReturn(testCase);
        when(caseMapper.updateById(any(Case.class))).thenReturn(1);
        when(caseStatusLogMapper.insert(any(CaseStatusLog.class))).thenReturn(1);

        CaseStatusVO status = caseService.updateStatus(1L, CaseStatusEnum.PENDING.name(), "admin", "测试");

        assertNotNull(status);
        assertEquals(CaseStatusEnum.PENDING, status.getStatus());
        verify(caseMapper).updateById(any(Case.class));
        verify(caseStatusLogMapper).insert(any(CaseStatusLog.class));
    }

    @Test
    void findByLawyer() {
        when(caseMapper.selectList(any())).thenReturn(Arrays.asList(testCase));

        List<CaseDetailVO> cases = caseService.findByLawyer("admin", new CaseQuery());

        assertNotNull(cases);
        assertFalse(cases.isEmpty());
        assertEquals(1, cases.size());
    }

    @Test
    void findByClient() {
        when(caseMapper.selectList(any())).thenReturn(Arrays.asList(testCase));

        List<CaseDetailVO> cases = caseService.findByClient(1L, new CaseQuery());

        assertNotNull(cases);
        assertFalse(cases.isEmpty());
        assertEquals(1, cases.size());
    }

    @Test
    void countByStatus() {
        when(caseMapper.selectList(any())).thenReturn(Arrays.asList(testCase));

        Map<String, Long> counts = caseService.countByStatus();

        assertNotNull(counts);
        assertFalse(counts.isEmpty());
        assertTrue(counts.containsKey(testCase.getCaseStatus().getDescription()));
    }
} 