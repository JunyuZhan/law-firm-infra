package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.WorkflowApplication;
import com.lawfirm.core.workflow.model.ProcessDefinition;
import com.lawfirm.core.workflow.model.ProcessInstance;
import com.lawfirm.core.workflow.model.Task;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 工作流服务集成测试
 */
@SpringBootTest(classes = WorkflowApplication.class)
@ActiveProfiles("test")
class WorkflowServiceIntegrationTest {

    @Autowired
    private WorkflowService workflowService;
    
    @Autowired
    private TaskService taskService;
    
    private String processDefinitionId;
    
    @BeforeEach
    void setUp() throws Exception {
        // 部署测试流程
        try (InputStream bpmnStream = new ClassPathResource("processes/test-process.bpmn20.xml").getInputStream()) {
            byte[] bpmnBytes = bpmnStream.readAllBytes();
            MultipartFile file = new MockMultipartFile(
                "test-process.bpmn20.xml",
                "test-process.bpmn20.xml",
                "application/xml",
                bpmnBytes
            );
            processDefinitionId = workflowService.deploy(file, "测试流程", "test", "test-tenant")
                    .getId();
        }
    }
    
    @Test
    @Transactional
    void testCompleteWorkflowProcess() {
        // 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicant", "张三");
        variables.put("amount", 10000);
        
        ProcessInstance processInstance = workflowService.startProcess(processDefinitionId, "TEST-001", variables, "admin");
        assertNotNull(processInstance);
        
        // 查询待办任务
        org.flowable.task.api.Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .taskAssignee("admin")
                .taskTenantId("test-tenant")
                .singleResult();
        assertNotNull(task);
        assertEquals("提交申请", task.getName());
        
        // 完成任务
        variables.clear();
        variables.put("approved", true);
        taskService.complete(task.getId(), variables);
        
        // 验证流程已结束
        assertNull(workflowService.getProcessInstance(processInstance.getId()));
    }
    
    @Test
    @Transactional
    void testRejectWorkflowProcess() {
        // 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicant", "李四");
        variables.put("amount", 20000);
        
        ProcessInstance processInstance = workflowService.startProcess(processDefinitionId, "TEST-002", variables, "admin");
        assertNotNull(processInstance);
        
        // 查询待办任务
        org.flowable.task.api.Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .taskAssignee("admin")
                .taskTenantId("test-tenant")
                .singleResult();
        assertNotNull(task);
        assertEquals("提交申请", task.getName());
        
        // 拒绝任务
        variables.clear();
        variables.put("approved", false);
        variables.put("reason", "金额超限");
        taskService.complete(task.getId(), variables);
        
        // 验证流程已结束
        assertNull(workflowService.getProcessInstance(processInstance.getId()));
    }
} 