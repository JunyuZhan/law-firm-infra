package com.lawfirm.cases.service;

import com.lawfirm.cases.exception.CaseException;
import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import com.lawfirm.model.cases.query.CaseQuery;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Sql("/sql/case-test-data.sql")
class CaseServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private CaseService caseService;

    private Case testCase;

    @BeforeEach
    void setUp() {
        clearDatabase();
        setupTestData();
        
        testCase = new Case();
        testCase.setCaseNumber("TEST-001");
        testCase.setCaseName("测试案件");
        testCase.setCaseStatus(CaseStatusEnum.DRAFT);
        testCase.setPrincipalId("test-lawyer");
        testCase.setClientId(1L);
        testCase.setCaseType(CaseTypeEnum.CIVIL);
        
        testCase = caseService.create(testCase);
    }

    @Test
    void testCaseLifecycle() {
        // 验证案件
        caseService.validateCase(testCase.getId(), "test-operator");
        CaseStatusVO status = caseService.getCurrentStatus(testCase.getId());
        assertEquals(CaseStatusEnum.PENDING, status.getStatus());

        // 开始处理
        caseService.updateStatus(testCase.getId(), CaseStatusEnum.IN_PROGRESS.name(), "test-operator", null);
        status = caseService.getCurrentStatus(testCase.getId());
        assertEquals(CaseStatusEnum.IN_PROGRESS, status.getStatus());

        // 暂停案件
        caseService.suspendCase(testCase.getId(), "test-operator", "测试暂停");
        status = caseService.getCurrentStatus(testCase.getId());
        assertEquals(CaseStatusEnum.SUSPENDED, status.getStatus());

        // 恢复案件
        caseService.resumeCase(testCase.getId(), "test-operator");
        status = caseService.getCurrentStatus(testCase.getId());
        assertEquals(CaseStatusEnum.IN_PROGRESS, status.getStatus());

        // 完成案件
        caseService.updateStatus(testCase.getId(), CaseStatusEnum.COMPLETED.name(), "test-operator", null);
        status = caseService.getCurrentStatus(testCase.getId());
        assertEquals(CaseStatusEnum.COMPLETED, status.getStatus());

        // 归档案件
        caseService.archiveCase(testCase.getId(), "test-operator");
        status = caseService.getCurrentStatus(testCase.getId());
        assertEquals(CaseStatusEnum.ARCHIVED, status.getStatus());

        // 验证状态历史
        List<CaseStatusVO> history = caseService.getStatusHistory(testCase.getId());
        assertFalse(history.isEmpty());
        assertTrue(history.size() >= 6);
    }

    @Test
    void testCaseQueries() {
        // 按律师查询
        List<CaseDetailVO> lawyerCases = caseService.findByLawyer("test-lawyer", new CaseQuery());
        assertFalse(lawyerCases.isEmpty());
        assertEquals(1, lawyerCases.size());

        // 按客户查询
        List<CaseDetailVO> clientCases = caseService.findByClient(1L, new CaseQuery());
        assertFalse(clientCases.isEmpty());
        assertEquals(1, clientCases.size());

        // 查询相关案件
        List<CaseDetailVO> relatedCases = caseService.findRelatedCases(testCase.getId());
        assertNotNull(relatedCases);
    }

    @Test
    void testCaseStatistics() {
        // 按状态统计
        Map<String, Long> statusStats = caseService.countByStatus();
        assertNotNull(statusStats);
        assertTrue(statusStats.containsKey(CaseStatusEnum.DRAFT.getDescription()));

        // 按类型统计
        Map<String, Long> typeStats = caseService.countByType();
        assertNotNull(typeStats);
        assertTrue(typeStats.containsKey(CaseTypeEnum.CIVIL.getDescription()));

        // 按律师统计
        Map<String, Long> lawyerStats = caseService.countByLawyer();
        assertNotNull(lawyerStats);
        assertTrue(lawyerStats.containsKey("test-lawyer"));

        // 按客户统计
        Map<String, Long> clientStats = caseService.countByClient();
        assertNotNull(clientStats);
        assertTrue(clientStats.containsKey("1"));
    }

    @Test
    void testStatusTransitions() {
        // 获取可用状态
        List<String> availableStatus = caseService.getAvailableStatus(testCase.getId());
        assertNotNull(availableStatus);
        assertTrue(availableStatus.contains(CaseStatusEnum.PENDING.name()));

        // 测试无效状态转换
        assertThrows(IllegalStateException.class, () -> {
            caseService.updateStatus(testCase.getId(), CaseStatusEnum.CLOSED.name(), "test-operator", null);
        });
    }

    // 新增测试用例：参数校验
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void testValidateOperator(String operator) {
        assertThrows(ConstraintViolationException.class, () -> {
            caseService.validateCase(testCase.getId(), operator);
        });
    }

    @Test
    void testValidateNullId() {
        assertThrows(ConstraintViolationException.class, () -> {
            caseService.validateCase(null, "test-operator");
        });
    }

    // 新增测试用例：业务规则验证
    @Test
    void testReopenArchivedCase() {
        // 先归档案件
        caseService.archiveCase(testCase.getId(), "test-operator");
        
        // 尝试重新打开
        assertThrows(IllegalStateException.class, () -> {
            caseService.reopenCase(testCase.getId(), "test-operator", "测试重开");
        });
    }

    @Test
    void testSuspendDraftCase() {
        // 尝试暂停草稿状态的案件
        assertThrows(IllegalStateException.class, () -> {
            caseService.suspendCase(testCase.getId(), "test-operator", "测试暂停");
        });
    }

    // 新增测试用例：边界条件
    @Test
    void testCaseNotFound() {
        assertThrows(CaseException.class, () -> {
            caseService.validateCase(999L, "test-operator");
        });
    }

    @ParameterizedTest
    @EnumSource(CaseStatusEnum.class)
    void testAllStatusTransitions(CaseStatusEnum targetStatus) {
        // 测试所有可能的状态转换
        try {
            caseService.updateStatus(testCase.getId(), targetStatus.name(), "test-operator", "测试转换");
        } catch (IllegalStateException e) {
            // 期望某些状态转换会失败
            assertTrue(e.getMessage().contains("Invalid status transition"));
        }
    }

    // 新增测试用例：查询条件测试
    @Test
    void testQueryWithFilters() {
        CaseQuery query = new CaseQuery();
        query.setCaseType(CaseTypeEnum.CIVIL);
        query.setStatus(CaseStatusEnum.DRAFT);
        
        List<CaseDetailVO> cases = caseService.findByLawyer("test-lawyer", query);
        assertFalse(cases.isEmpty());
        cases.forEach(caseVO -> {
            assertEquals(CaseTypeEnum.CIVIL, caseVO.getCaseType());
            assertEquals(CaseStatusEnum.DRAFT, caseVO.getStatus());
        });
    }

    @Test
    void testEmptyQueryResults() {
        CaseQuery query = new CaseQuery();
        query.setCaseType(CaseTypeEnum.CRIMINAL);
        query.setStatus(CaseStatusEnum.CLOSED);
        
        List<CaseDetailVO> cases = caseService.findByLawyer("test-lawyer", query);
        assertTrue(cases.isEmpty());
    }

    // 新增测试用例：状态历史记录
    @Test
    void testStatusHistoryOrder() {
        // 执行一系列状态变更
        caseService.validateCase(testCase.getId(), "test-operator");
        caseService.updateStatus(testCase.getId(), CaseStatusEnum.IN_PROGRESS.name(), "test-operator", null);
        caseService.suspendCase(testCase.getId(), "test-operator", "测试暂停");
        
        List<CaseStatusVO> history = caseService.getStatusHistory(testCase.getId());
        assertFalse(history.isEmpty());
        
        // 验证历史记录的顺序（最新的在前）
        assertEquals(CaseStatusEnum.SUSPENDED, history.get(0).getStatus());
        assertEquals(CaseStatusEnum.IN_PROGRESS, history.get(1).getStatus());
        assertEquals(CaseStatusEnum.PENDING, history.get(2).getStatus());
    }

    // 新增测试用例：并发操作
    @Test
    void testConcurrentStatusUpdate() {
        // 模拟并发更新状态
        assertThrows(Exception.class, () -> {
            Thread t1 = new Thread(() -> caseService.validateCase(testCase.getId(), "operator1"));
            Thread t2 = new Thread(() -> caseService.validateCase(testCase.getId(), "operator2"));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        });
    }
} 